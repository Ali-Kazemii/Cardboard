package ir.nik.cardboard.view.createletter.receiver.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.Status
import ir.awlrhm.modules.extentions.failureOperation
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.utils.OnStatusListener
import ir.awlrhm.modules.view.ActionDialog
import ir.awlrhm.modules.view.RecyclerView
import ir.nik.cardboard.data.network.model.request.CardboardCaseReferralListByWFSCaseIdRequest
import ir.nik.cardboard.data.network.model.request.CardboardDeleteCaseReferRequest
import ir.nik.cardboard.data.network.model.response.CardboardCaseReferralListByWFSCaseIdResponse
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.caseReferralListByWFSCaseId
import ir.nik.cardboard.view.base.CardboardBaseFragment
import ir.nik.cardboard.view.createletter.CreateLetterViewModel
import kotlinx.android.synthetic.main.fragment_letter_receiver.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class LetterReceiverFragment(
    private val caseId: Long,
    private val listener: OnActionListener
) : CardboardBaseFragment(), OnStatusListener {

    private val viewModel by viewModel<CreateLetterViewModel>()

    private var pageNumber: Int = 1

    override fun setup() {
        rclReceiver.layoutManager(LinearLayoutManager(activity))
        rclReceiver.onActionListener(object: RecyclerView.OnActionListener{
            override fun onRefresh() {
                if(!rclReceiver.isOnLoading)
                    rclReceiver.showLoading()
                getReceivers()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_letter_receiver, container, false)
    }

    override fun onResume() {
        super.onResume()
        if(!rclReceiver.isOnLoading)
            rclReceiver.showLoading()
        getReceivers()
    }

    private fun getReceivers() {
        viewModel.getCaseReferralListByWFSCaseId(
            CardboardCaseReferralListByWFSCaseIdRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.pageNumber = pageNumber
                request.typeOperation = 103
                request.jsonParameters = caseReferralListByWFSCaseId(
                    caseId = caseId
                )
            }
        )
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.listCaseReferralByWfsCaseResponse.observe(viewLifecycleOwner,{
            it.result?.let { list ->
                if (list.isNotEmpty()) {
                    onStatus(Status.SUCCESS)
                    rclReceiver.view?.adapter = Adapter(
                        list,
                        object : Adapter.OnActionListener {
                            override fun onEdit(model: CardboardCaseReferralListByWFSCaseIdResponse.Result) {
                                listener.onEdit(model)
                            }

                            override fun onDelete(crId: Long) {
                                ActionDialog.Builder()
                                    .setTitle(getString(R.string.warning))
                                    .setDescription(getString(R.string.are_you_sure_delete))
                                    .setPositive(getString(R.string.yes)) {
                                        rclReceiver .actionLoading = true
                                        viewModel.deleteCaseRefer(
                                            CardboardDeleteCaseReferRequest().also { request ->
                                                request.wfsCrId = crId
                                                request.userId = viewModel.userId
                                                request.financialYearId = viewModel.financialYear
                                            }
                                        )
                                    }
                                    .setNegative(getString(R.string.no)) {}
                                    .build()
                                    .show(activity.supportFragmentManager, ActionDialog.TAG)

                            }
                        }
                    )

                }else {
                    onStatus(Status.FAILURE)
                    rclReceiver.showNoData()
                }
            }?: kotlin.run {
                rclReceiver.showNoData()
            }
        })

        viewModel.responseId.observe(viewLifecycleOwner,{ response ->
            onStatus(Status.SUCCESS)
            response.result?.let {
                if (it != 0L) {
                    rclReceiver.showLoading()
                    getReceivers()
                } else
                    activity.failureOperation(response.message)
            } ?: kotlin.run { activity.failureOperation(response.message) }
        })
    }

    override fun handleOnClickListeners() {
        btnAdd.setOnClickListener {
            listener.onAdd()
        }
        btnBack.setOnClickListener { activity?.onBackPressed() }
    }

    override fun onStatus(status: Status) {
        when(status){
            Status.LOADING -> {
               rclReceiver .actionLoading = true
            }
            else ->
                rclReceiver.actionLoading = false
        }
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner,{
            onStatus(Status.FAILURE)
            rclReceiver.showNoData()
             activity?.showError(it?.message)
        })
        viewModel.errorDelete.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                onStatus(Status.FAILURE)
                activity?.showError(it?.message)
            }
        })
    }

    interface OnActionListener {
        fun onAdd()
        fun onEdit(model: CardboardCaseReferralListByWFSCaseIdResponse.Result)
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${LetterReceiverFragment::class.java.simpleName}"
    }
}