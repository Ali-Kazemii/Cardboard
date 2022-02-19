package ir.nik.cardboard.view.casedetail.report

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.yToast
import ir.nik.cardboard.data.network.model.request.CaseDetailBodyRequest
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.caseDetailBodyJson
import ir.nik.cardboard.utils.historyReportJson
import ir.nik.cardboard.view.base.BaseFragment
import ir.nik.cardboard.view.casedetail.CaseDetailViewModel
import kotlinx.android.synthetic.main.contain_report.*
import kotlinx.android.synthetic.main.fragment_report.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CaseDetailReport(
    private val reportName: String?
): BaseFragment(), OnPageErrorListener {

    constructor(
        reportName: String?,
        wfsCrId: Long?
    ): this(reportName){
        this.wfsCrId = wfsCrId
    }

    constructor(
        reportName: String?,
        wfsCrId: Long?,
        wfsCaseId: Long?,
    ): this(reportName){
        this.wfsCrId = wfsCrId
        this.wfsCaseId = wfsCaseId
        isHistoryReport = true
    }

    private val viewModel by viewModel<CaseDetailViewModel>()
    private var pdfStream: String? = null

    private var wfsCrId: Long? = null
    private var wfsCaseId: Long? = null
    private var isHistoryReport: Boolean = false


    override fun setup() {
        txtTitle.text = getString(R.string.report)
        txtSubtitle.text = reportName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCaseReport()
    }

    private fun getCaseReport() {
        showLoading()

        viewModel.getCaseDetailBody(
            CaseDetailBodyRequest().also { request ->
                request.wfsCrId = wfsCrId
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.typeOperation = if(isHistoryReport) 303 else 302
                request.jsonParameters = if(isHistoryReport) historyReportJson(wfsCaseId = wfsCaseId)
                else caseDetailBodyJson()
            }
        )
    }


    override fun handleObservers() {
        viewModel.reportBody.observe(this, {
            prcReport.isVisible = false
            if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { report ->
                    pdfStream = report
                    loadPdf()

                } ?: kotlin.run {
                    showPdf(false)
                }
            }
        })
    }

    override fun handleOnClickListeners() {
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun loadPdf() {
        showPdf(true)
        pdfView.fromBytes(Base64.decode(pdfStream, Base64.DEFAULT))
            .enableAnnotationRendering(true)
            .spacing(10) // in dp
            .onPageError(this)
            .enableAnnotationRendering(false)
            .enableAntialiasing(true)
            .load()
    }

    private fun showPdf(visible: Boolean) {
        if (visible) {
            prcReport.isVisible = false
            pdfView.isVisible = true
            layoutNoData.isVisible = false

        } else {
            prcReport.isVisible = false
            pdfView.isVisible = false
            layoutNoData.isVisible = true
        }
    }

    private fun showLoading() {
        prcReport.isVisible = true
        pdfView.isVisible = false
        layoutNoData.isVisible = false
    }

    override fun onPageError(page: Int, t: Throwable?) {
        activity?.yToast(
            getString(R.string.failed_load_report),
            MessageStatus.ERROR
        )
        showPdf(false)
    }

    override fun handleError() {
        super.handleError()
        viewModel.errorBody.observe(viewLifecycleOwner, {
            showPdf(false)
            activity?.showError(
                it?.message ?: getString(R.string.response_error),
            )
        })
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${CaseDetailReport::class.java.simpleName}"
    }

}