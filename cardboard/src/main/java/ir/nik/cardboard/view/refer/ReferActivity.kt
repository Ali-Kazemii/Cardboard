package ir.nik.cardboard.view.refer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.*
import ir.nik.cardboard.data.network.model.request.CaseReferWithJsonRequest
import ir.nik.cardboard.data.network.model.response.CardboardInformationResponse
import ir.nik.cardboard.data.network.model.utt.UTTWFSModel
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.convertUTTWFSModelToJson
import ir.nik.cardboard.view.base.ChildActivity
import ir.nik.cardboard.view.cardboard.CardboardActivity
import kotlinx.android.synthetic.main.activity_refer.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ReferActivity : ChildActivity() {

    private val viewModel by viewModel<ReferViewModel>()
    private var list: MutableList<ReferModel> = mutableListOf()
    private val listUtt: MutableList<UTTWFSModel> = mutableListOf()
    private var listCaseInfo: MutableList<CardboardInformationResponse.Result> = mutableListOf()


    override fun setup() {
        configToolbar(toolbar)
        setTitle(R.string.refer)

        listCaseInfo =
            intent.getSerializableExtra(Const.KEY_REFER) as MutableList<CardboardInformationResponse.Result>

        rclRefer.layoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        rclRefer.view?.adapter = Adapter(list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_refer)
        super.onCreate(savedInstanceState)

        showAddRefer(true)
    }

    override fun setTitle(title: CharSequence) {
        txtTitle.text = title
    }

    override fun handleObservers() {
        viewModel.responseId.observe(this,{ response ->
            rclRefer.actionLoading = false

            response.result?.let {
                if(it != 0L){
                    yToast(
                        response.message ?: getString(R.string.success_operation),
                        MessageStatus.SUCCESS
                    )
//                    gotoCaseList()
                    onBackPressed()
                }else
                    failureOperation(response.message)
            }?: kotlin.run {
                failureOperation(response.message)
            }
        })
    }

    private fun gotoCaseList() {
        val intent = Intent(this@ReferActivity, CardboardActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun handleOnClickListeners() {
        btnAdd.setOnClickListener {
            showAddRefer(true)
        }
        btnSend.setOnClickListener {
           rclRefer.actionLoading = true

            viewModel.postCaseReferWithJson(
                CaseReferWithJsonRequest().apply {
                    this.wfsCrIdJson = convertUTTWFSModelToJson(listUtt)
                    this.financialYearId = viewModel.financialYear
                    this.userId = viewModel.personnelId
                    this.typeOperation = Const.ReferOtherOperation.KEY_NEXT_STEP
                }
            )
        }
    }

    private fun showAddRefer(visible: Boolean) {
        if (visible) {
            referFragment.visibility = View.VISIBLE
            rclRefer.visibility = View.GONE
            replaceFragmentInActivity(
                R.id.referFragment,
                AddReferFragment(
                    listCaseInfo[0].wfsCrId ?: 0,
                    listCaseInfo[0].wfsProcessId ?: 0,
                    listCaseInfo[0].wfsProcessTableName ?: ""
                ) { referModel, uttModel ->

                    listCaseInfo.forEach { model ->
                        listUtt.add(UTTWFSModel().also { request ->
                            request.rowNum = 1
                            request.wfsCrId = model.wfsCrId ?: 0
                            request.wfsCaseId = model.wfsCaseId ?: 0
                            request.wfsPreviousId = model.wfsPsNextId ?: 0
                            request.userIdSender = uttModel.userIdSender
                            request.wfsProcessId = uttModel.wfsProcessId
                            request.userIdReceiver = uttModel.userIdReceiver
                            request.postIdSender = uttModel.postIdSender
                            request.postIdReceiver = uttModel.postIdReceiver
                            request.wfsNextId = uttModel.wfsNextId
                            request.wfsCrPriorityId = uttModel.wfsCrPriorityId
                            request.wfsRtId = uttModel.wfsRtId
                            request.wfsDt = uttModel.wfsDt
                            request.wfsCrReply = uttModel.wfsCrReply
                            request.wfsCrNote = uttModel.wfsCrNote
                            request.wfsCrPursuitDateTime = uttModel.wfsCrPursuitDateTime
                            request.wfsCrType = uttModel.wfsCrType
                            request.wfsCrStatus = uttModel.wfsCrStatus
                            request.isValid = uttModel.isValid
                            request.wfsCrSubject = uttModel.wfsCrSubject
                            request.wfsNrId = uttModel.wfsNrId
                        })
                    }

                    showAddRefer(false)
                    list.add(referModel)
                    rclRefer.view?.adapter = Adapter(list)
                },
                AddReferFragment.TAG
            )
            setTitle(getString(R.string.add_refer))
            btnAdd.visibility = View.GONE
            btnSend.visibility = View.GONE

        } else {
            supportFragmentManager.popBackStack()
            toolbar.menu.clear()
            setTitle(R.string.refer)
            btnAdd.visibility = View.VISIBLE
            btnSend.visibility = View.VISIBLE
            referFragment.visibility = View.GONE
            rclRefer.visibility = View.VISIBLE
        }
    }


    override fun handleError() {
        super.handleError()
        viewModel.error.observe(this,{
            rclRefer.actionLoading = false
            showError(it?.message)
        })
        viewModel.errorPostCaseRefer.observe(this, {
            rclRefer.actionLoading = false
            showError(it?.message)
        })
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            showAddRefer(false)
        } else
            this.finish()
    }
}
