package ir.nik.cardboard.view.cardboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.showError
import ir.nik.cardboard.data.network.model.base.BaseGetRequest
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.cardboardListJson
import ir.nik.cardboard.view.base.BaseFragment
import ir.nik.cardboard.view.dialog.CaseFilterDialog
import kotlinx.android.synthetic.main.contain_cardboard.*
import kotlinx.android.synthetic.main.fragment_cardboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CardBoardFragment(
    private val callback: (ssId: Long, name: String) -> Unit
) : BaseFragment() {

    private val viewModel by viewModel<CardboardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cardboard, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return

        btnBack.setOnClickListener {
            activity.onBackPressed()
        }
        layoutDate.setOnClickListener {
            CaseFilterDialog(mutableListOf()) {
                getDashboardMenu()
                configDocumentDate()
            }.show(activity.supportFragmentManager, CaseFilterDialog.TAG)
        }

    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.cardboardListResponse.observe(viewLifecycleOwner, {
            it.result?.let { list ->
                val adapter = Adapter(list)
                { id, name ->
                    callback.invoke(
                        id, name ?: ""
                    )
                }
                rclDashboard.layoutManager(GridLayoutManager(activity, 3))
                rclDashboard.view?.adapter = adapter
            } ?: kotlin.run {
                rclDashboard.showNoData()
            }
        })
    }

    private fun getDashboardMenu() {
        rclDashboard.showLoading()
        viewModel.getCardboardList(
            BaseGetRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.typeOperation = 19
                request.jsonParameters =
                    cardboardListJson(
                        viewModel.documentStartDate,
                        viewModel.documentEndDate
                    )
            }
        )
    }

    override fun setup() {}

    override fun onStart() {
        super.onStart()
        configDocumentDate()
        getDashboardMenu()
    }

    private fun configDocumentDate() {
        txtDocumentStartDate.text = viewModel.documentStartDate
        txtDocumentEndDate.text = viewModel.documentEndDate
    }

    override fun handleError() {
        super.handleError()
        val activity = activity ?: return
        viewModel.error.observe(viewLifecycleOwner, {
            rclDashboard.showNoData()
            activity.showError(it?.message)
        })
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${CardBoardFragment::class.java.simpleName}"
    }
}