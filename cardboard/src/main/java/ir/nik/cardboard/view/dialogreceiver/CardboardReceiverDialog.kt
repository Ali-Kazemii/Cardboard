package ir.nik.cardboard.view.dialogreceiver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nik.cardboard.R
import ir.nik.cardboard.data.network.model.base.BaseGetRequest
import ir.nik.cardboard.data.network.model.response.CardboardReceiverResponse
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.receiverJson
import kotlinx.android.synthetic.main.dialog_receiver_list_cardboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CardboardReceiverDialog(
    private val wfsCrId: Long,
    private val wfsPsNextId: Long,
    private val callback: (CardboardReceiverResponse.Result) -> Unit
) : DialogFragment() {

    private val viewModel by viewModel<CardboardReceiverViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_receiver_list_cardboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity ?: return

        btnSearch.setOnClickListener {

        }

        viewModel.getReceivers(
            BaseGetRequest().also { request ->
               request.userId = viewModel.userId
               request.typeOperation = 15
               request.jsonParameters = receiverJson(wfsCrId, wfsPsNextId)
           }
        )
        viewModel.receiverListResponse.observe(viewLifecycleOwner,{
            it.result?.let { list ->
                rclReceiver.layoutManager(
                    LinearLayoutManager(activity))
                rclReceiver.view?.adapter = CardboardReceiverAdapter(list) { receiver ->
                    callback.invoke(receiver)
                    dismiss()
                }
            }

        })
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${CardboardReceiverDialog::class.java.simpleName}"
    }
}