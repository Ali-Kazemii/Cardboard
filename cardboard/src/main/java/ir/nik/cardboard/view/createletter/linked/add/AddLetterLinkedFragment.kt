package ir.nik.cardboard.view.createletter.linked.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.enums.Status
import ir.awlrhm.modules.extentions.*
import ir.awlrhm.modules.models.ItemModel
import ir.awlrhm.modules.utils.OnStatusListener
import ir.awlrhm.modules.view.ChooseDialog
import ir.nik.cardboard.data.network.model.request.CardboardDocumentRelationTypeRequest
import ir.nik.cardboard.data.network.model.request.CardboardPostCaseLinkedListJsonRequest
import ir.nik.cardboard.data.network.model.request.CardboardUserLetterListRequest
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.documentRelationTypeJson
import ir.nik.cardboard.utils.getLetterLinkedJson
import ir.nik.cardboard.view.base.CardboardBaseFragment
import ir.nik.cardboard.view.createletter.CreateLetterViewModel
import kotlinx.android.synthetic.main.contain_letter_linked_cardboard.*
import kotlinx.android.synthetic.main.fragment_letter_add_linked_cardboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class AddLetterLinkedFragment(
    private var wfsCaseId: Long
) : CardboardBaseFragment(), OnStatusListener {

    private val viewModel by viewModel<CreateLetterViewModel>()

    private var listLetters: MutableList<ItemModel> = mutableListOf()
    private var listRelationType: MutableList<ItemModel> = mutableListOf()

    private var relationTypeId: Long = 0
    private var wfsCaseLinkedId: Long = 0

    override fun setup() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_letter_add_linked_cardboard, container, false)
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.userLetterListResponse.observe(viewLifecycleOwner,{
            it.result?.let { list ->
                list.forEach { model ->
                    listLetters.apply {
                        add(
                            ItemModel(model.wfsCaseId ?: 0, model.letterTitle ?: "")
                        )
                    }
                }
                showUserLetterList()

            } ?: kotlin.run {
                spLetters.failedData()
            }
        })
        viewModel.documentRelationTypeResponse.observe(viewLifecycleOwner,{
            it.result?.let { list ->
                list.forEach { model ->
                    listRelationType.apply {
                        add(
                            ItemModel(model.wfsBaseId ?: 0, model.wfsBaseName ?: "")
                        )
                    }
                }
                showRelationType()

            } ?: kotlin.run {
                spRelationType.failedData()
            }
        })
        viewModel.responseId.observe(viewLifecycleOwner,{
            if (it.result != 0L) {
                onStatus(Status.SUCCESS)
                activity.showFlashbar(
                    getString(R.string.success),
                    it.message ?: getString(R.string.success_operation),
                    MessageStatus.SUCCESS
                )

            } else {
                activity.failureOperation(it.message)
                onStatus(Status.FAILURE)
            }
        })
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return

        btnBack.setOnClickListener { activity.onBackPressed() }
        spLetters.setOnClickListener {
            getLetters()
        }
        spRelationType.setOnClickListener {
            getRelationType()
        }
        btnDone.setOnClickListener {
            if (relationTypeId != 0L) {
                onStatus(Status.LOADING)
                viewModel.postCaseLinkedListWithJson(
                    CardboardPostCaseLinkedListJsonRequest().also { request ->
                        request.wfsCaseId = wfsCaseId
                        request.financialYearId = viewModel.financialYear
                        request.json = getLetterLinkedJson(
                            wfsCaseLinkedId,
                            relationTypeId
                        )
                        request.registerDate = viewModel.currentDate
                        request.userId = viewModel.userId
                    }
                )
            } else
                activity.yToast(getString(R.string.fill_all_blanks), MessageStatus.ERROR)
        }
    }

    private fun getRelationType() {
        if (listRelationType.isEmpty()) {
            spRelationType.loading(true)
            viewModel.getDocumentRelationTypeList(
                CardboardDocumentRelationTypeRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 10
                    request.jsonParameters = documentRelationTypeJson()
                }
            )

        } else
            showRelationType()
    }

    private fun showRelationType() {
        val activity = activity ?: return
        spRelationType.loading(false)

        if (listRelationType.isNotEmpty())
            ChooseDialog(listRelationType) {
                spRelationType.text = it.title
                relationTypeId = it.id
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun getLetters() {
        if (listLetters.isEmpty()) {
            spLetters.loading(true)
            viewModel.getUserLetterList(
                CardboardUserLetterListRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 14
                }
            )
        } else
            showUserLetterList()
    }

    private fun showUserLetterList() {
        val activity = activity ?: return

        spLetters.loading(false)
        if (listLetters.isNotEmpty())
            ChooseDialog(listLetters) {
                wfsCaseLinkedId = it.id
                spLetters.text = it.title
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    override fun onStatus(status: Status) {
        when (status) {
            Status.LOADING -> {
                btnDone.isVisible = false
                prcDone.isVisible = true
            }
            else -> {
                btnDone.isVisible = true
                prcDone.isVisible = false
                spLetters.loading(false)
                spRelationType.loading(false)
            }
        }
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner,{
            onStatus(Status.FAILURE)
             activity?.showError(it?.message)
        })
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${AddLetterLinkedFragment::class.java.simpleName}"
    }
}