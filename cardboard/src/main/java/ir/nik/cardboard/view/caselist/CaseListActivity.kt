package ir.nik.cardboard.view.caselist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.nik.cardboard.BuildConfig
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.*
import ir.awlrhm.modules.models.ItemModel
import ir.awlrhm.modules.view.RecyclerView
import ir.nik.cardboard.data.network.model.request.CardboardInformationRequest
import ir.nik.cardboard.data.network.model.request.CaseReadRequest
import ir.nik.cardboard.data.network.model.request.CaseReferWithJsonRequest
import ir.nik.cardboard.data.network.model.request.ProcessRequest
import ir.nik.cardboard.data.network.model.response.CardboardInformationResponse
import ir.nik.cardboard.data.network.model.utt.UTTWFSModel
import ir.nik.cardboard.utils.*
import ir.nik.cardboard.utils.Const.KEY_CARTABLE_INFO
import ir.nik.cardboard.utils.Const.KEY_CASE_LIST_TYPE
import ir.nik.cardboard.utils.Const.KEY_REFER
import ir.nik.cardboard.view.base.ChildActivity
import ir.nik.cardboard.view.casedetail.CaseDetailActivity
import ir.nik.cardboard.view.createletter.CreateLetterActivity
import ir.nik.cardboard.view.dialog.CaseFilterDialog
import ir.nik.cardboard.view.dialog.NoteDialog
import ir.nik.cardboard.view.refer.ReferActivity
import ir.nik.cardboard.view.search.SearchActivity
import kotlinx.android.synthetic.main.activity_case_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

internal class CaseListActivity : ChildActivity() {

    private val viewModel by viewModel<CaseListViewModel>()

    private var listProcessStep: MutableList<ItemModel> = mutableListOf()

    private var wfsProcessId: Long = 0
    private var model: CaseListModel? = null
    private var adapter: Adapter? = null

    private var pageNumber: Int = 1
    private var totalCount: Long = 0

    override fun setup() {
        if (BuildConfig.DEBUG)
            fabAddLetter.isVisible = true

        configToolbar(toolbar)

        rclCaseList.layoutManager(
            LinearLayoutManager(this)
        )

        model = intent.getSerializableExtra(KEY_CASE_LIST_TYPE) as CaseListModel
        setTitle(model?.caseName ?: getString(R.string.document_kartable))
    }

    private fun getCaseList() {
        when (model?.caseType) {
            CaseType.DOCUMENT ->
                getCardboardDocumentInformationList()

            CaseType.SENT ->
                getCardboardSentList()

            else -> yToast(getString(R.string.not_fount_sub_system), MessageStatus.INFORMATION)
        }
    }


    private fun getCardboardDocumentInformationList() {
        viewModel.getCardboardInformationList(
            CardboardInformationRequest().also { request ->
                request.userId = viewModel.userId
                request.pageNumber = pageNumber
                request.financialYearId = viewModel.financialYear
                request.typeOperation = 101
                request.jsonParameters =
                    cardboardInformationJson(
                        startRange = viewModel.documentStartDate,
                        endRange = viewModel.documentEndDate,
                        documentStatusId = model?.documentStatusId ?: 0,
                        wfsProcessId = wfsProcessId,
                        search = ""
                    )
            }
        )
    }


    private fun getCardboardSentList() {
        viewModel.getCardboardInformationList(
            CardboardInformationRequest().also { request ->
                request.userId = viewModel.userId
                request.pageNumber = pageNumber
                request.financialYearId = viewModel.financialYear
                request.typeOperation = 101
                request.jsonParameters =
                    cardboardInformationJson(
                        startRange = viewModel.documentStartDate,
                        endRange = viewModel.documentEndDate,
                        documentStatusId = 982050, //سرویس های جدید معادل 14000830 سطر 92
                        wfsProcessId = wfsProcessId,
                        search = ""
                    )
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_case_list)
        super.onCreate(savedInstanceState)

        initialList()

        getCaseList()
    }


    private fun initialList() {
        adapter = Adapter { result, isUnread ->
            if (isUnread)
                viewModel.postCaseRead(
                    CaseReadRequest().also { request ->
                        request.wfsCrId = result.wfsCrId
                        request.userId = viewModel.userId
                        request.financialYearId = viewModel.financialYear
                        request.typeOperation = 1
                    }
                )

            val intent =
                Intent(this@CaseListActivity, CaseDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(KEY_CARTABLE_INFO, result)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        adapter?.let { adapter ->
            rclCaseList.onActionRecyclerViewListener(object :
                RecyclerView.OnRecyclerViewListener {
                override fun onScrolled(
                    recyclerView: androidx.recyclerview.widget.RecyclerView,
                    dx: Int,
                    dy: Int
                ) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()
                    val endHasBeenReached = lastVisible + 1 >= totalItemCount
                    if (totalItemCount > 0 && endHasBeenReached && totalCount > adapter.itemCount) {
                        loading(true)
                        pageNumber += 1
                        getCardboardDocumentInformationList()
                    }
                }
            })
        }
    }

    private fun loading(visible: Boolean) {
        loading.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun handleOnClickListeners() {
        fabAddLetter.setOnClickListener {
            startActivity(Intent(this@CaseListActivity, CreateLetterActivity::class.java))
        }
        btnSearch.setOnClickListener {
            startActivity(Intent(this@CaseListActivity, SearchActivity::class.java))
        }
        btnFilter.setOnClickListener {
            getProcessStepList()
        }
        btnGroup.setOnClickListener {
            showGroupMenu(true)
        }
        btnApprove.setOnClickListener {

            adapter?.let {
                val list = it.getSelectedItems()
                if (list.size > 0) {
                    list[0].approveStep?.let { message ->
                        showFlashbar(
                            "",
                            message,
                            MessageStatus.ERROR
                        )

                    } ?: kotlin.run {
                        showActionFlashbar(
                            getString(R.string.warning),
                            getString(R.string.are_you_sure_approve_end)
                        ) {
                            val listUtt: MutableList<UTTWFSModel> = mutableListOf()
                            list.forEach { item ->
                                listUtt.add(UTTWFSModel().also { request ->
                                    request.wfsCrId = item.wfsCrId ?: 0
                                    request.wfsCaseId = item.wfsCaseId ?: 0
                                    request.wfsProcessId = item.wfsProcessId ?: 0
                                })
                            }

                            viewModel.postCaseReferWithJson(
                                CaseReferWithJsonRequest().also { request ->
                                    request.wfsCrIdJson =
                                        convertUTTWFSModelToJson(listUtt)
                                    request.typeOperation =
                                        Const.ReferOtherOperation.KEY_APPROVE_END
                                    request.financialYearId = viewModel.financialYear
                                    request.userId = viewModel.personnelId
                                }
                            )
                        }
                    }
                } else
                    showFlashbar(
                        "",
                        getString(R.string.select_item),
                        MessageStatus.ERROR
                    )
            }
        }
        btnDisapprove.setOnClickListener {

            adapter?.let {
                val list = it.getSelectedItems()
                if (list.size > 0) {
                    list[0].abortStep?.let { abort ->
                        showFlashbar(
                            "",
                            abort,
                            MessageStatus.ERROR
                        )
                    } ?: kotlin.run {
                        showActionFlashbar(
                            getString(R.string.warning),
                            getString(R.string.are_you_sure_disapprove_end)
                        ) {
                            NoteDialog { crNote ->

                                val listUtt: MutableList<UTTWFSModel> = mutableListOf()
                                list.forEach { item ->
                                    listUtt.add(UTTWFSModel().also { request ->
                                        request.wfsCrId = item.wfsCrId ?: 0
                                        request.wfsCaseId = item.wfsCaseId ?: 0
                                        request.wfsProcessId = item.wfsProcessId ?: 0
                                        request.wfsCrNote = crNote
                                    })
                                }

                                viewModel.postCaseReferWithJson(
                                    CaseReferWithJsonRequest().also { request ->
                                        request.wfsCrIdJson = convertUTTWFSModelToJson(listUtt)
                                        request.typeOperation =
                                            Const.ReferOtherOperation.KEY_DISAPPROVE_END
                                        request.financialYearId = viewModel.financialYear
                                        request.userId = viewModel.personnelId
                                    }
                                )
                            }.show(supportFragmentManager, NoteDialog.TAG)
                        }
                    }
                } else
                    showFlashbar(
                        "",
                        getString(R.string.select_item),
                        MessageStatus.ERROR
                    )
            }
        }
        btnRefer.setOnClickListener {
            adapter?.getSelectedItems()?.let { list ->

                if (list.size > 1) {
                    var hasRefer = false
                    var isTeamate = true
                    var referMessage = ""

                    list.forEach lit@{
                        if (!it.nextStep.isNullOrEmpty()) {
                            referMessage = it.nextStep
                            hasRefer = false
                            return@lit
                        } else
                            hasRefer = true
                    }

                    if (hasRefer) {
                        list.forEachIndexed lit@{ index, _ ->
                            if (index + 1 < list.size)
                                if (list[index].wfsProcessId != list[index + 1].wfsProcessId) {
                                    isTeamate = false
                                    return@lit
                                }
                        }
                        if (isTeamate) {
                            gotoReferActivity(list)

                        } else {
                            showFlashbar(
                                "",
                                getString(R.string.selected_items_not_teamate),
                                MessageStatus.ERROR
                            )
                        }
                    } else
                        showFlashbar(
                            "",
                            referMessage,
                            MessageStatus.ERROR
                        )
                } else if (list.size == 1) {
                    if (list[0].nextStep.isNullOrEmpty())
                        gotoReferActivity(list)
                    else
                        list[0].nextStep?.let { nextStep ->
                            showFlashbar(
                                "",
                                nextStep,
                                MessageStatus.ERROR
                            )
                        }
                } else
                    showFlashbar(
                        "",
                        getString(R.string.select_item),
                        MessageStatus.ERROR
                    )
            }
        }
    }

    private fun getProcessStepList() {
        if (listProcessStep.isEmpty()) {
            btnFilter.loading(true)
            viewModel.getProcessList(
                ProcessRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 12
                    request.jsonParameters = processJson(
                        documentStatusId = model?.documentStatusId ?: 0
                    )
                }
            )
        } else
            showProcessStepList()
    }

    private fun gotoReferActivity(list: MutableList<CardboardInformationResponse.Result>) {
        showGroupMenu(false)
        val intent = Intent(this, ReferActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(KEY_REFER, list as Serializable)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun showGroupMenu(visible: Boolean) {
        adapter?.let {
            if (visible) {
                layoutMenu.visibility = View.GONE
                layoutGroup.visibility = View.VISIBLE
                setTitle(getString(R.string.choose))
                it.setSelectableItems(true)
            } else {
                layoutGroup.visibility = View.GONE
                layoutMenu.visibility = View.VISIBLE
                setTitle(model?.caseName ?: getString(R.string.document_kartable))
                it.setSelectableItems(false)
            }
        }
    }

    override fun handleObservers() {
        viewModel.listCaseResponse.observe(this, { response ->
            response.result?.let { list ->
                if (list.size > 0) {
                    totalCount = response.resultCount ?: 0
                    loading(false)
                    if (adapter?.itemCount == 0)
                        rclCaseList.view?.adapter = adapter
                    adapter?.setSource(list)
                } else
                    rclCaseList.showNoData()

            } ?: kotlin.run {
                rclCaseList.showNoData()
            }
        })

        viewModel.listProcessResponse.observe(this, { response ->
            btnFilter.loading(false)
            response.result?.let { list ->
                list.forEachIndexed { index, result ->
                    listProcessStep.add(
                        index,
                        ItemModel(
                            result.valueMember ?: 0,
                            result.textMember ?: ""
                        )
                    )
                }
                showProcessStepList()
            } ?: kotlin.run {
                yToast(
                    getString(R.string.failed_operation),
                    MessageStatus.ERROR
                )
            }
        })

        viewModel.responseId.observe(this, { response ->
            response.result?.let {
                if (it != 0L) {
                    showFlashbar(
                        getString(R.string.success),
                        response.message ?: getString(R.string.success_operation),
                        MessageStatus.SUCCESS
                    )
                    refreshList()
                    showGroupMenu(false)
                } else
                    failureOperation(response.message)
            } ?: kotlin.run {
                failureOperation(response.message)
            }
        })
    }

    private fun showProcessStepList() {
        CaseFilterDialog(listProcessStep) { id ->
            wfsProcessId = id
            refreshList()
        }.show(supportFragmentManager, CaseFilterDialog.TAG)
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        if (!rclCaseList.isOnLoading)
            rclCaseList.showLoading()
        adapter?.clear()
        getCaseList()
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(this, {
            btnFilter.loading(visible = false)
            rclCaseList.showNoData()
            showError(it?.message)
        })
        viewModel.errorPostCaseRefer.observe(this, {
            showError(it?.message)
        })
    }


    fun setTitle(title: String) {
        txtTitle.text = title
    }

    override fun onBackPressed() {
        if (layoutMenu.visibility == View.GONE)
            showGroupMenu(false)
        else
            this.finish()
    }
}
