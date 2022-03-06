package ir.nik.cardboard.view.casedetail.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.showError
import ir.nik.cardboard.data.network.model.request.CardboardCaseReferHistoryRequest
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.caseReferHistoryJson
import ir.nik.cardboard.view.base.CardboardBaseFragment
import ir.nik.cardboard.view.casedetail.CardboardCaseDetailViewModel
import kotlinx.android.synthetic.main.fragment_case_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CardboardCaseReferHistoryFragment(
    private val wfsCaseId: Long,
    private  val listener: ()-> Unit
) : CardboardBaseFragment() {

    private val viewModel by viewModel<CardboardCaseDetailViewModel>()

    private var pageNumber = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_case_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCaseHistory()
    }

    override fun handleOnClickListeners() {
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        btnReport.setOnClickListener {
            listener.invoke()
        }
    }

    private fun getCaseHistory() {
        viewModel.getCaseReferHistory(
            CardboardCaseReferHistoryRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.typeOperation = 102
                request.pageNumber = pageNumber
                request.jsonParameters = caseReferHistoryJson(wfsCaseId)
            }
        )
    }

    override fun setup() {
        val activity = activity ?: return
        rclHistory.layoutManager(LinearLayoutManager(activity))
    }

    override fun handleObservers() {
        viewModel.listHistory.observe(viewLifecycleOwner, {
            rclHistory.view?.adapter = CardboardAdapter(it)
        })
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(this, {
            rclHistory?.showNoData()
            activity?.showError(it?.message)
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${CardboardCaseReferHistoryFragment::class.java.simpleName}"
    }
}