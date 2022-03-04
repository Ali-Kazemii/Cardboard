package ir.nik.cardboard.view.cardboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.view.ActionDialog
import ir.nik.cardboard.data.network.model.base.BaseGetRequest
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.SSID
import ir.nik.cardboard.utils.cardboardListJson
import ir.nik.cardboard.view.base.BaseFragment
import ir.nik.cardboard.view.dialog.CaseFilterDialog
import kotlinx.android.synthetic.main.contain_cardboard.*
import kotlinx.android.synthetic.main.fragment_cardboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.StringBuilder

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


    private var bannerClickCount = 0
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

        imgBanner.setOnClickListener {
            bannerClickCount = bannerClickCount.plus(1)
            if(bannerClickCount < 5)
                return@setOnClickListener

            val str = StringBuilder()
            str.append("SSId: ${SSID}\n")
            str.append("UserId: ${viewModel.userId}\n")
            str.append("personnelId: ${viewModel.personnelId}\n")
            str.append("imei: ${viewModel.imei}\n")
            str.append("financialYear: ${viewModel.financialYear}\n")
            str.append("financialYear: ${viewModel.financialYear}\n")
            str.append("startDate: ${viewModel.documentStartDate}, endDate: ${viewModel.documentEndDate}\n\n")
            str.append("Autentication: ${viewModel.accessToken}\n")
            ActionDialog.Builder()
                .setTitle("Params:")
                .setAction(MessageStatus.INFORMATION)
                .setDescription(str.toString())
                .build()
                .show(activity.supportFragmentManager, ActionDialog.TAG)

            bannerClickCount = 0
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