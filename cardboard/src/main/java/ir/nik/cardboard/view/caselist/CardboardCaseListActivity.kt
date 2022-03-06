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
import ir.nik.cardboard.data.network.model.request.CardboardCaseReadRequest
import ir.nik.cardboard.data.network.model.request.CardboardCaseReferWithJsonRequest
import ir.nik.cardboard.data.network.model.request.CardboardProcessRequest
import ir.nik.cardboard.data.network.model.response.CardboardInformationResponse
import ir.nik.cardboard.data.network.model.utt.CardboardUttWfsModel
import ir.nik.cardboard.utils.*
import ir.nik.cardboard.utils.Const.KEY_CARTABLE_INFO
import ir.nik.cardboard.utils.Const.KEY_CASE_LIST_TYPE
import ir.nik.cardboard.utils.Const.KEY_DOCUMENT_SSID
import ir.nik.cardboard.utils.Const.KEY_REFER
import ir.nik.cardboard.view.base.CardboardChildActivity
import ir.nik.cardboard.view.casedetail.CardboardCaseDetailActivity
import ir.nik.cardboard.view.createletter.CreateLetterActivity
import ir.nik.cardboard.view.dialog.CardboardCaseFilterDialog
import ir.nik.cardboard.view.dialog.CardboardNoteDialog
import ir.nik.cardboard.view.gateway.model.CaseType
import ir.nik.cardboard.view.refer.CardboardReferActivity
import ir.nik.cardboard.view.search.SearchActivity
import kotlinx.android.synthetic.main.activity_case_list_cardboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

internal class CardboardCaseListActivity : CardboardChildActivity() {

    private val viewModel by viewModel<CardboardCaseListViewModel>()

    private var listProcessStep: MutableList<ItemModel> = mutableListOf()

    private var wfsProcessId: Long = 0
    private var model: CardboardCaseListModel? = null
    private var adapter: CardboardAdapter? = null

    private var pageNumber: Int = 1
    private var totalCount: Long = 0

    override fun setup() {
        if (BuildConfig.DEBUG)
            fabAddLetter.isVisible = true

        model = intent.getSerializableExtra(KEY_CASE_LIST_TYPE) as CardboardCaseListModel
        setTitle(model?.caseName ?: getString(R.string.document_kartable))

        configToolbar(toolbar)

        rclCaseList.layoutManager(
            LinearLayoutManager(this)
        )
        initialList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_case_list_cardboard)
        super.onCreate(savedInstanceState)

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
                        documentStatusId = model?.documentSsId,
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



    private fun initialList() {
        adapter = CardboardAdapter { result, isUnread ->
            if (isUnread)
                viewModel.postCaseRead(
                    CardboardCaseReadRequest().also { request ->
                        request.wfsCrId = result.wfsCrId
                        request.userId = viewModel.userId
                        request.financialYearId = viewModel.financialYear
                        request.typeOperation = 1
                    }
                )

            val intent =
                Intent(this@CardboardCaseListActivity, CardboardCaseDetailActivity::class.java)
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
            startActivity(Intent(this@CardboardCaseListActivity, CreateLetterActivity::class.java))
        }
        btnSearch.setOnClickListener {
            val intent = Intent(this@CardboardCaseListActivity, SearchActivity::class.java)
            val bundle = Bundle()
            bundle.putLong(KEY_DOCUMENT_SSID, model?.documentSsId?: 0)
            intent.putExtras(bundle)
            startActivity(intent)
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
                            val listUtt: MutableList<CardboardUttWfsModel> = mutableListOf()
                            list.forEach { item ->
                                listUtt.add(CardboardUttWfsModel().also { request ->
                                    request.wfsCrId = item.wfsCrId ?: 0
                                    request.wfsCaseId = item.wfsCaseId ?: 0
                                    request.wfsProcessId = item.wfsProcessId ?: 0
                                })
                            }

                            viewModel.postCaseReferWithJson(
                                CardboardCaseReferWithJsonRequest().also { request ->
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
                            CardboardNoteDialog { crNote ->

                                val listUtt: MutableList<CardboardUttWfsModel> = mutableListOf()
                                list.forEach { item ->
                                    listUtt.add(CardboardUttWfsModel().also { request ->
                                        request.wfsCrId = item.wfsCrId ?: 0
                                        request.wfsCaseId = item.wfsCaseId ?: 0
                                        request.wfsProcessId = item.wfsProcessId ?: 0
                                        request.wfsCrNote = crNote
                                    })
                                }

                                viewModel.postCaseReferWithJson(
                                    CardboardCaseReferWithJsonRequest().also { request ->
                                        request.wfsCrIdJson = convertUTTWFSModelToJson(listUtt)
                                        request.typeOperation =
                                            Const.ReferOtherOperation.KEY_DISAPPROVE_END
                                        request.financialYearId = viewModel.financialYear
                                        request.userId = viewModel.personnelId
                                    }
                                )
                            }.show(supportFragmentManager, CardboardNoteDialog.TAG)
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
                CardboardProcessRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 12
                    request.jsonParameters = processJson(
                        documentStatusId = model?.documentSsId ?: 0
                    )
                }
            )
        } else
            showProcessStepList()
    }

    private fun gotoReferActivity(list: MutableList<CardboardInformationResponse.Result>) {
        showGroupMenu(false)
        val intent = Intent(this, CardboardReferActivity::class.java)
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
        CardboardCaseFilterDialog(listProcessStep) { id ->
            wfsProcessId = id
            refreshList()
        }.show(supportFragmentManager, CardboardCaseFilterDialog.TAG)
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
