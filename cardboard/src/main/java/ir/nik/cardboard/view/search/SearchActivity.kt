package ir.nik.cardboard.view.search

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.*
import ir.nik.cardboard.data.network.model.request.CardboardInformationRequest
import ir.nik.cardboard.data.network.model.request.CardboardCaseReadRequest
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.cardboardInformationJson
import ir.nik.cardboard.view.base.CardboardChildActivity
import ir.nik.cardboard.view.casedetail.CardboardCaseDetailActivity
import ir.nik.cardboard.view.caselist.CardboardAdapter
import ir.nik.cardboard.view.caselist.CardboardCaseListViewModel

import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SearchActivity : CardboardChildActivity() {

    private val viewModel by viewModel<CardboardCaseListViewModel>()
    private lateinit var adapter: CardboardAdapter

    private var pageNumber = 1
    private var documentSsId: Long?= null



    override fun setup() {
         documentSsId = intent.extras?.getLong(Const.KEY_DOCUMENT_SSID)

        rclSearch.layoutManager(LinearLayoutManager(this@SearchActivity))
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
            val intent = Intent(this@SearchActivity, CardboardCaseDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(Const.KEY_CARTABLE_INFO, result)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_search)
        super.onCreate(savedInstanceState)

        edtSearch.requestFocus()
        showKeyboard()

        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
                search()
            true
        }
        btnBack.setOnClickListener {
            if (edtSearch.text.isEmpty()) {
                hideKeyboard(layoutSearch)
                onBackPressed()
            } else
                onStatus(Status.SEARCH)

        }
        btnSearch.setOnClickListener { search() }

        viewModel.listCaseResponse.observe(this, {
            it.result?.let { list ->
                if (list.size > 0) {
                    adapter.setSource(list)
                    rclSearch?.view?.adapter = adapter

                } else
                    onStatus(Status.FAILURE)
            } ?: kotlin.run {
                onStatus(Status.FAILURE)
            }
        })
    }

    private fun search() {
        val search = edtSearch.text.toString()
        if (search.isNotEmpty()) {
            onStatus(Status.LOADING)

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
                            documentStatusId = documentSsId,
                            wfsProcessId = 0,
                            search = search
                        )
                }
            )
        } else
            yToast(
                getString(R.string.fill_search_key),
                MessageStatus.ERROR
            )
    }

    private fun onStatus(status: Status) {
        when (status) {
            Status.LOADING -> {
                logoSearch.isVisible = false
                rclSearch.isVisible = true
                rclSearch.showLoading()
            }
            Status.FAILURE -> {
                rclSearch.showNoData()
            }
            Status.SEARCH -> {
                logoSearch.isVisible = true
                rclSearch.isVisible = false
                edtSearch.setText("")
            }
        }
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(this, {
            onStatus(Status.FAILURE)
            showError(it?.message)
        })
    }

    enum class Status {
        SEARCH,
        LOADING,
        FAILURE
    }
}
