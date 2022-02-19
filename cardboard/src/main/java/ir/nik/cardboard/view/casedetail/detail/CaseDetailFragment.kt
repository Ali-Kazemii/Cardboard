package ir.nik.cardboard.view.casedetail.detail

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.*
import ir.nik.cardboard.data.network.model.request.CaseDetailBodyRequest
import ir.nik.cardboard.data.network.model.request.CaseDetailInformationRequest
import ir.nik.cardboard.data.network.model.request.CaseReferWithJsonRequest
import ir.nik.cardboard.data.network.model.response.CardboardInformationResponse
import ir.nik.cardboard.data.network.model.utt.UTTWFSModel
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.caseDetailBodyJson
import ir.nik.cardboard.utils.convertUTTWFSModelToJson
import ir.nik.cardboard.view.base.BaseFragment
import ir.nik.cardboard.view.casedetail.CaseDetailViewModel
import ir.nik.cardboard.view.dialog.NoteDialog
import kotlinx.android.synthetic.main.contain_case_detail.*
import kotlinx.android.synthetic.main.fragment_case_detail.*
import kotlinx.android.synthetic.main.fragment_case_detail.btnBack
import kotlinx.android.synthetic.main.fragment_report.*
import kotlinx.android.synthetic.main.item_information.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CaseDetailFragment(
    private var model: CardboardInformationResponse.Result?,
    private val listener: OnActionListener
) : BaseFragment(), OnPageErrorListener {


    private val viewModel by viewModel<CaseDetailViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_case_detail, container, false)
    }

    override fun onResume() {
        super.onResume()

        if (layoutInformation.childCount == 0) {
            actionLoading.isVisible = true
            getCaseDetailInformation()
        }
    }


    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.caseInformationResponse.observe(this, {
            actionLoading.isVisible = false

            it.result?.let { list ->
                val data = list[0].textMember
                if (data.isNullOrEmpty())
                    activity.showError(getString(R.string.no_data))
                else {
                    initDataLayout(data)

                    callReport()
                }
            }
        })

        viewModel.reportBody.observe(this, {
            prcReport.isVisible = false
            if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { pdf ->
                    showPdf(pdf)

                } ?: kotlin.run {
                    pdfReport.isVisible = false
                }
            }
        })

        viewModel.responseId.observe(this, { response ->
            response.result?.let {
                if (it != 0L)
                    activity.successOperation(
                        response.message ?: getString(R.string.success_operation)
                    )
                else
                    activity.failureOperation(response.message)
            } ?: kotlin.run {
                activity.failureOperation(response.message)
            }
        })
    }


    override fun handleOnClickListeners() {
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        btnApprove.setOnClickListener {
            approveStep()
        }

        btnDisapprove.setOnClickListener {
            disapproveStep()
        }

        btnRefer.setOnClickListener {
            refer()
        }
        btnCaseHistory.setOnClickListener {
            listener.onCaseHistory(
                wfsCrId = model?.wfsCrId,
                wfsCaseId = model?.wfsCaseId,
                reportName = model?.wfS_ProcessFarsiName,
            )
        }
        btnReport.setOnClickListener {
            listener.onCaseReport(
                reportName = model?.wfS_ProcessFarsiName,
                wfsCrId = model?.wfsCrId
            )
        }
    }


    private fun callReport() {
        prcReport.isVisible = true

        getCaseBody()
    }


    private fun initDataLayout(data: String) {
        val activity = activity ?: return

        data.split(",").forEachIndexed { index, value ->
            val view = LayoutInflater.from(activity)
                .inflate(R.layout.item_information, layoutInformation, false)
            view.txtInformation.text = value
            val color = if (index % 2 == 0)
                R.color.grey_200
            else
                R.color.white

            view.layoutInformation.setBackgroundColor(
                ContextCompat.getColor(
                    activity,
                    color
                )
            )
            layoutInformation.addView(view)
        }
    }


    private fun showPdf(pdf: String) {
        pdfReport.fromBytes(Base64.decode(pdf, Base64.DEFAULT))
            .enableAnnotationRendering(true)
            .spacing(10) // in dp
            .onPageError(this)
            .enableAnnotationRendering(false)
            .enableAntialiasing(true)
            .load()
    }


    private fun refer() {
        val activity = activity ?: return

        if (model != null) {
            model!!.nextStep?.let { nextStep ->
                activity.showFlashbar(
                    "",
                    nextStep,
                    MessageStatus.ERROR
                )
            } ?: kotlin.run {
                listener.onRefer(model!!)
            }
        }
    }


    private fun approveStep() {
        val activity = activity ?: return

        model?.approveStep?.let { approveStep ->
            activity.showFlashbar(
                "",
                approveStep,
                MessageStatus.ERROR
            )
        } ?: kotlin.run {
            activity.showActionFlashbar(
                getString(R.string.warning),
                getString(R.string.are_you_sure_approve_end)
            ) {
                callApproveCase()
            }
        }
    }


    private fun callApproveCase() {
        viewModel.postCaseReferWithJson(
            CaseReferWithJsonRequest().also { request ->
                request.wfsCrIdJson =
                    convertUTTWFSModelToJson(mutableListOf<UTTWFSModel>().also { list ->
                        list.add(UTTWFSModel().also { utt ->
                            utt.wfsCrId = model?.wfsCrId ?: 0
                            utt.wfsCaseId = model?.wfsCaseId ?: 0
                            utt.wfsProcessId = model?.wfsProcessId ?: 0
                        })
                    })
                request.typeOperation = Const.ReferOtherOperation.KEY_APPROVE_END
                request.financialYearId = viewModel.financialYear
                request.userId = viewModel.userId
            }
        )
    }


    private fun disapproveStep() {
        val activity = activity ?: return

        model?.abortStep?.let { abort ->
            activity.showFlashbar(
                "",
                abort,
                MessageStatus.ERROR
            )
        } ?: kotlin.run {
            activity.showActionFlashbar(
                getString(R.string.warning),
                getString(R.string.are_you_sure_disapprove_end)
            ) {
                showNoteDialog()
            }
        }
    }


    private fun showNoteDialog() {
        val activity = activity ?: return

        NoteDialog { note ->
            callDisapproveCase(note)

        }.show(activity.supportFragmentManager, NoteDialog.TAG)
    }


    private fun callDisapproveCase(note: String) {
        viewModel.postCaseReferWithJson(
            CaseReferWithJsonRequest().also { request ->
                request.wfsCrIdJson =
                    convertUTTWFSModelToJson(
                        mutableListOf<UTTWFSModel>().apply {
                            add(UTTWFSModel().also { uttModel ->
                                uttModel.wfsCrId = model?.wfsCrId ?: 0
                                uttModel.wfsCaseId = model?.wfsCaseId ?: 0
                                uttModel.wfsProcessId = model?.wfsProcessId ?: 0
                                uttModel.wfsCrNote = note
                            })
                        })
                request.typeOperation = Const.ReferOtherOperation.KEY_DISAPPROVE_END
                request.financialYearId = viewModel.financialYear
                request.userId = viewModel.personnelId
            }
        )
    }


    private fun getCaseBody() {
        viewModel.getCaseDetailBody(
            CaseDetailBodyRequest().also { request ->
                request.wfsCrId = model?.wfsCrId
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.typeOperation = 301
                request.jsonParameters = caseDetailBodyJson()
            }
        )
    }


    private fun getCaseDetailInformation() {
        viewModel.getCaseDetailInformation(
            CaseDetailInformationRequest().also { request ->
                request.wfsCrId = model?.wfsCrId
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.typeOperation = 501
            }
        )
    }


    override fun onPageError(page: Int, t: Throwable?) {
        prcReport.isVisible = false
        activity?.yToast(getString(R.string.error_loading_pdf), MessageStatus.ERROR)
    }


    override fun handleError() {
        super.handleError()

        viewModel.error.observe(viewLifecycleOwner, {
            actionLoading.isVisible = false
            activity?.showError(it?.message)
        })

        viewModel.errorBody.observe(viewLifecycleOwner, {
            prcReport.isVisible = false
        })
    }


    internal interface OnActionListener {
        fun onRefer(
            model: CardboardInformationResponse.Result
        )

        fun onCaseHistory(
            wfsCrId: Long?,
            wfsCaseId: Long?,
            reportName: String?
        )

        fun onCaseReport(
            wfsCrId: Long?,
            reportName: String?
        )
    }


    companion object {
        val TAG = "${Const.APP_NAME}: ${CaseDetailFragment::class.java.simpleName}"
    }
}