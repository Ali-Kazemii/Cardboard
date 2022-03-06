package ir.nik.cardboard.view.fastrefer.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.showActionFlashbar
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.successOperation
import ir.nik.cardboard.data.network.model.request.CardboardDeleteFastReferRequest
import ir.nik.cardboard.data.network.model.request.CardboardFastReferRequest
import ir.nik.cardboard.data.network.model.response.CardboardFastReferResponse
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.fastReferJson
import ir.nik.cardboard.view.base.CardboardBaseFragment
import ir.nik.cardboard.view.fastrefer.CardboardFastReferViewModel
import kotlinx.android.synthetic.main.fragment_fast_refer.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CardboardFastReferFragment(
    private val listener: OnActionListener
) : CardboardBaseFragment(), View.OnClickListener {

    private val viewModel by viewModel<CardboardFastReferViewModel>()
    private var pageNumber: Int = 1


    override fun setup() {
        txtTitle.text = getString(R.string.fast_refer)
        rclFastRefer.layoutManager(LinearLayoutManager(activity))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fast_refer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAdd.setOnClickListener(this)
        btnBack.setOnClickListener(this)
    }

    private fun getFastRefers() {

        viewModel.getFastRefers(
            CardboardFastReferRequest().also { request ->
                request.userId = viewModel.userId
                request.pageNumber = pageNumber
                request.financialYearId = viewModel.financialYear
                request.typeOperation = 101
                request.jsonParameters = fastReferJson(viewModel.userId)
            }
        )
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.refersList.observe(viewLifecycleOwner,{
            it.result?.let { list ->
                if (list.isNotEmpty()) {
                    val adapter =
                        CardboardAdapter(
                            list,
                            object : CardboardAdapter.OnActionListener {
                                override fun onDelete(ufrId: Long) {
                                    activity.showActionFlashbar(
                                        getString(R.string.delete),
                                        getString(R.string.are_you_sure_delete)
                                    ) {
                                        rclFastRefer.actionLoading = true
                                        viewModel.deleteFastRefer(
                                            CardboardDeleteFastReferRequest().also { request ->
                                                request.ufrId = ufrId
                                                request.userId = viewModel.userId
                                                request.financialYearId = viewModel.financialYear
                                            }
                                        )
                                    }
                                }

                                override fun onEdit(model: CardboardFastReferResponse.Result) {
                                    listener.onEdit(model)
                                }
                            })
                    rclFastRefer.view?.adapter = adapter
                } else
                    rclFastRefer.showNoData()

            } ?: kotlin.run {
                rclFastRefer.showNoData()
            }
        })
        viewModel.responseId.observe(viewLifecycleOwner, {
            rclFastRefer.actionLoading = false
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { result ->
                    if (result != 0L) {
                        rclFastRefer.showLoading()
                        getFastRefers()
                        activity.successOperation()

                    } else
                        activity.showError(it.message)
                } ?: kotlin.run {
                    activity.showError(it.message)
                }
            }
        })
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnAdd -> listener.onAdd()
            R.id.btnBack -> activity?.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!rclFastRefer.isOnLoading)
            rclFastRefer.showLoading()
        getFastRefers()
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner,{
            rclFastRefer.showNoData()
            activity?.showError(it?.message)
        })
        viewModel.errorDelete.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                rclFastRefer.actionLoading = false
                activity?.showError(it?.message)
            }
        })
    }

    interface OnActionListener {
        fun onAdd()
        fun onEdit(model: CardboardFastReferResponse.Result)
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${CardboardFastReferFragment::class.java.simpleName}"
    }
}