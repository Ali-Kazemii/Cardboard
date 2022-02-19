package ir.nik.cardboard.view.createletter.draft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.Status
import ir.awlrhm.modules.extentions.failureOperation
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.successOperation
import ir.awlrhm.modules.utils.OnStatusListener
import ir.awlrhm.modules.view.ActionDialog
import ir.nik.cardboard.data.network.model.request.DeleteLetterRequest
import ir.nik.cardboard.data.network.model.request.DraftLetterRequest
import ir.nik.cardboard.data.network.model.request.PostFinalSaveLetterRequest
import ir.nik.cardboard.data.network.model.response.DraftLetterResponse
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.view.base.BaseFragment
import ir.nik.cardboard.view.createletter.CreateLetterViewModel
import kotlinx.android.synthetic.main.fragment_letter_drafts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class LetterDraftFragment(
    private val listener: OnActionListener
) : BaseFragment(), OnStatusListener {

    private val viewModel by viewModel<CreateLetterViewModel>()

    private var pageNumber = 1

    override fun setup() {
        rclLetterDraft.layoutManager(LinearLayoutManager(activity))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_letter_drafts, container, false)
    }

    override fun onResume() {
        super.onResume()
        getDraftLetters()
    }

    private fun getDraftLetters() {
        if (!rclLetterDraft.isOnLoading)
        rclLetterDraft.showLoading()
        viewModel.getDraftLetterList(
            DraftLetterRequest().also { request ->
                request.userId = viewModel.userId
                request.pageNumber = pageNumber
                request.financialYearId = viewModel.financialYear
                request.typeOperation = 101
            }
        )
    }

    override fun handleOnClickListeners() {
        btnAdd.setOnClickListener {
            listener.onAdd()
        }
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.draftLetterListResponse.observe(viewLifecycleOwner, {
            it.result?.let { list ->
                if (list.isNotEmpty()) {
                    onStatus(Status.SUCCESS)
                    rclLetterDraft.view?.adapter = Adapter(
                        list,
                        object : Adapter.OnActionListener {
                            override fun onEdit(model: DraftLetterResponse.Result) {
                                listener.onEdit(model)
                            }

                            override fun onDelete(letterId: Long) {
                                ActionDialog.Builder()
                                    .setTitle(getString(R.string.warning))
                                    .setDescription(getString(R.string.are_you_sure_delete))
                                    .setPositive(getString(R.string.yes)) {
                                        rclLetterDraft.actionLoading = true
                                        viewModel.deleteLetter(
                                            DeleteLetterRequest().also { request ->
                                                request.letterId = letterId
                                                request.userId = viewModel.userId
                                                request.financialYearId = viewModel.financialYear
                                            }
                                        )
                                    }
                                    .setNegative(getString(R.string.no)) {}
                                    .build()
                                    .show(activity.supportFragmentManager, ActionDialog.TAG)
                            }

                            override fun onLetterInformation(letterId: Long) {
                                listener.onLetterInformation(letterId)
                            }

                            override fun onAttachment(letterId: Long) {
                                listener.onAttachment(letterId)
                            }

                            override fun onReceivers(wfsCaseId: Long) {
                                listener.onReceivers(wfsCaseId)
                            }

                            override fun onLetterLinked(wfsCaseId: Long) {
                                listener.onLetterLinked(wfsCaseId)
                            }

                            override fun onLetterExtraInformation(
                                letterId: Long,
                                customerId: Long
                            ) {
                                listener.onLetterExtraInformation(letterId, customerId)
                            }

                            override fun onLetterSend(letterId: Long) {
                                rclLetterDraft.actionLoading = true
                                viewModel.postFinalSaveLetter(
                                    PostFinalSaveLetterRequest().also { request ->
                                        request.oasLetterId = letterId
                                        request.userId = viewModel.userId
                                        request.financialYearId = viewModel.financialYear
                                        request.typeOperation = 1
                                    }
                                )

                            }
                        }
                    )
                } else {
                    onStatus(Status.FAILURE)
                    rclLetterDraft.showNoData()
                }
            } ?: kotlin.run {
                onStatus(Status.FAILURE)
                rclLetterDraft.showNoData()
            }
        })
        viewModel.responseId.observe(viewLifecycleOwner, { response ->
            rclLetterDraft.actionLoading = false
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let {
                    if (it != 0L) {
                        activity.successOperation(response.message)
                        getDraftLetters()

                    } else {
                        activity.failureOperation(response.message)
                    }
                } ?: kotlin.run {
                    activity.failureOperation(response.message)
                }
            }
        })
    }

    override fun onStatus(status: Status) {
        when (status) {
            Status.LOADING -> rclLetterDraft.actionLoading = true
            else -> rclLetterDraft.actionLoading = false
        }
    }

    override fun handleError() {
        super.handleError()
        val activity = activity ?: return

        viewModel.error.observe(viewLifecycleOwner, {
            rclLetterDraft.showNoData()
            activity.showError(it?.message)
        })
        viewModel.errorPostCaseRefer.observe(viewLifecycleOwner, {
            rclLetterDraft.actionLoading = false
            activity.showError(it?.message)
        })
        viewModel.errorDelete.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                rclLetterDraft.actionLoading = false
                activity.showError(it?.message)
            }
        })
    }

    interface OnActionListener {
        fun onAdd()
        fun onEdit(model: DraftLetterResponse.Result)
        fun onLetterInformation(letterId: Long)
        fun onAttachment(letterId: Long)
        fun onReceivers(wfsCaseId: Long)
        fun onLetterLinked(wfsCaseId: Long)
        fun onLetterExtraInformation(letterId: Long, customerId: Long)
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${LetterDraftFragment::class.java.simpleName}"
    }
}