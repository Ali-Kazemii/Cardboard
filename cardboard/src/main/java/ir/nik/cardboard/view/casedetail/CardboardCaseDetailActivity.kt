package ir.nik.cardboard.view.casedetail

import android.content.Intent
import android.os.Bundle
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.*
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.response.CardboardInformationResponse
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.Const.KEY_CARTABLE_INFO
import ir.nik.cardboard.view.base.CardboardChildActivity
import ir.nik.cardboard.view.casedetail.detail.CardboardCaseDetailFragment
import ir.nik.cardboard.view.casedetail.report.CardboardCaseDetailReportFragment
import ir.nik.cardboard.view.casedetail.timeline.CardboardCaseReferHistoryFragment
import ir.nik.cardboard.view.refer.CardboardReferActivity
import kotlinx.android.synthetic.main.activity_case_detail.*
import kotlinx.android.synthetic.main.contain_case_detail.*
import kotlinx.android.synthetic.main.item_information.view.*

internal class CardboardCaseDetailActivity : CardboardChildActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_case_detail)
        super.onCreate(savedInstanceState)

        val model =
            intent.extras?.getSerializable(KEY_CARTABLE_INFO) as CardboardInformationResponse.Result
        gotoCaseDetailFragment(model)
    }

    private fun gotoCaseDetailFragment(
        model: CardboardInformationResponse.Result
    ) {
        replaceFragmentInActivity(
            R.id.container,
            CardboardCaseDetailFragment(
                model = model,
                caseDetailListener
            ),
            CardboardCaseDetailFragment.TAG
        )
    }

    private val caseDetailListener = object : CardboardCaseDetailFragment.OnActionListener {

        override fun onRefer(model: CardboardInformationResponse.Result) {
            val intent = Intent(this@CardboardCaseDetailActivity, CardboardReferActivity::class.java)

            val bundle = Bundle()
            bundle.putSerializable(
                Const.KEY_REFER,
                arrayListOf<CardboardInformationResponse.Result>().apply {
                    this.add(model)
                }
            )
            intent.putExtras(bundle)
            startActivity(intent)
        }

        override fun onCaseHistory(wfsCrId: Long?, wfsCaseId: Long?, reportName: String?) {
            gotoCaseHistory(
                wfsCrId = wfsCrId,
                wfsCaseId = wfsCaseId,
                reportName = reportName
            )
        }

        override fun onCaseReport(wfsCrId: Long?, reportName: String?) {
            gotoCaseDetailReport(reportName = reportName, wfsCrId = wfsCrId)
        }
    }

    private fun gotoCaseDetailReport(reportName: String?, wfsCrId: Long?) {
        addFragmentInActivity(
            R.id.container,
            CardboardCaseDetailReportFragment(
                reportName = reportName,
                wfsCrId = wfsCrId
            ),
            CardboardCaseDetailReportFragment.TAG
        )
    }

    private fun gotoCaseHistory(wfsCaseId: Long?, wfsCrId: Long?, reportName: String?) {
        addFragmentInActivity(
            R.id.container,
            CardboardCaseReferHistoryFragment(
                wfsCaseId ?: 0
            ) {
                gotoCaseHistoryReport(
                    wfsCrId = wfsCrId,
                    wfsCaseId = wfsCaseId,
                    reportName = reportName
                )
            },
            CardboardCaseReferHistoryFragment.TAG
        )
    }

    private fun gotoCaseHistoryReport(
        wfsCrId: Long?,
        wfsCaseId: Long?,
        reportName: String?
    ) {
        addFragmentInActivity(
            R.id.container,
            CardboardCaseDetailReportFragment(
                reportName = reportName,
                wfsCrId = wfsCrId,
                wfsCaseId = wfsCaseId
            ),
            CardboardCaseDetailReportFragment.TAG
        )
    }


/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    val inflater = menuInflater
    inflater.inflate(R.menu.menu_case_detail, menu)
    return super.onCreateOptionsMenu(menu)
}
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_transfer_archive -> {
                ActionDialog.Builder()
                    .setTitle(getString(R.string.warning))
                    .setDescription(getString(R.string.are_you_sure_transfer_archive))
                    .setPositive(getString(R.string.yes)) {
                        viewModel.postTransferToFolder(
                            TransferToFolderRequest().also { request ->
                                request.userId = viewModel.userId
                                request.financialYearId = viewModel.financialYear
                                request.registerDate = viewModel.currentDate
                                request.dfId = 0                      //شناسه پوشه
                                request.dmsDfhId = 0                  //TODO: ask
                                request.documentId = model?.wfsCaseId ?: 0  //شناسه نامه
                            }
                        )
                    }
                    .setNegative(getString(R.string.no)) {}
                    .build()
                    .show(supportFragmentManager, ActionDialog.TAG)
            }
            R.id.action_fast_referral -> {
                actionLoading.isVisible = true

                viewModel.callControlValidation(
                    ControlValidationRequest().also { request ->
                        request.userId = viewModel.userId
                        request.financialYearId = viewModel.financialYear
                        request.typeOperation = 5
                        request.jsonParameters = getControlReferParameterJson(
                            model?.wfsProcessId ?: 0
                        )
                    }
                )
            }
            R.id.action_fast_sign -> {
                *//*menuVisibility(false)
                fragmentVisibility(true)

                setTitle(getString(R.string.fast_sign))
                setSubTitle("به شماره: ${model?.wfsCrId}")
                replaceFragmentInActivity(
                    R.id.cntDetail,
                    FastActionFragment(FastActionType.SIGNATURE),
                    FastActionFragment.TAG
                ) *//*
                        yToast(getString(R.string.in_next_version), MessageStatus.INFORMATION)
            }
            R.id.action_verification_sign -> {
                yToast(getString(R.string.in_next_version), MessageStatus.INFORMATION)
            }
            R.id.action_save_send -> {
                yToast(getString(R.string.in_next_version), MessageStatus.INFORMATION)
            }

            R.id.action_delete -> {
                yToast(getString(R.string.in_next_version), MessageStatus.INFORMATION)
            }
        }
        return super.onOptionsItemSelected(item)
    }
*/

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else
            this.finish()
    }
}