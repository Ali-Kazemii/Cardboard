package ir.nik.cardboard.view.createletter.linked.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.failureOperation
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.successOperation
import ir.awlrhm.modules.view.ActionDialog
import ir.nik.cardboard.data.network.model.request.CaseLinkedRequest
import ir.nik.cardboard.data.network.model.request.DeleteCaseLinkedRequest
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.caseLinkedJson
import ir.nik.cardboard.view.base.BaseFragment
import ir.nik.cardboard.view.createletter.CreateLetterViewModel
import kotlinx.android.synthetic.main.fragment_letter_linked.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class LetterLinkedFragment(
    private val wfsCaseId: Long,
    private val listener: OnActionListener
) : BaseFragment() {

    private val viewModel by viewModel<CreateLetterViewModel>()

    private var pageNumber = 1

    override fun setup() {
        rclLinked.layoutManager(LinearLayoutManager(activity))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_letter_linked, container, false)
    }

    private fun getLinkedLetter() {
        viewModel.getCaseLinkedList(
            CaseLinkedRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.typeOperation = 101
                request.pageNumber = pageNumber
                request.jsonParameters = caseLinkedJson(
                    wfsCaseId = wfsCaseId
                )
            }
        )
    }

    override fun onResume() {
        super.onResume()
        if (!rclLinked.isOnLoading)
            rclLinked.showLoading()
        getLinkedLetter()
    }

    override fun handleOnClickListeners() {
        btnAdd.setOnClickListener {
            listener.onAdd()
        }
        btnBack.setOnClickListener { activity?.onBackPressed() }
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.caseLinkedResponse.observe(viewLifecycleOwner,{
            it.result?.let { list ->
                if (list.isNotEmpty()) {
                    rclLinked.view?.adapter = Adapter(
                        list,
                        object : Adapter.OnActionListener {
                            override fun onDelete(clId: Long) {
                                ActionDialog.Builder()
                                    .setTitle(getString(R.string.warning))
                                    .setDescription(getString(R.string.are_you_sure_delete))
                                    .setPositive(getString(R.string.yes)) {

                                        rclLinked.actionLoading = true
                                        viewModel.deleteCaseLinked(
                                            DeleteCaseLinkedRequest().also { request ->
                                                request.clId = clId
                                                request.userId = viewModel.userId
                                                request.financialYearId = viewModel.financialYear
                                            }
                                        )
                                    }
                                    .setNegative(getString(R.string.no)) {}
                                    .build()
                                    .show(activity.supportFragmentManager, ActionDialog.TAG)

                            }
                        }
                    )
                } else
                    rclLinked.showNoData()
            } ?: kotlin.run {
                rclLinked.showNoData()
            }
        })

        viewModel.responseId.observe(viewLifecycleOwner, {
            if (it.result != 0L) {
                activity.successOperation(it.message)
                rclLinked.showLoading()
                getLinkedLetter()

            } else {
                activity.failureOperation(it.message)
            }
        })
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner,{
            rclLinked.showNoData()
             activity?.showError(it?.message)
        })
        viewModel.errorDelete.observe(viewLifecycleOwner,{
            activity?.showError(it?.message)
        })
    }

    interface OnActionListener {
        fun onAdd()
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${LetterLinkedFragment::class.java.simpleName}"
    }
}