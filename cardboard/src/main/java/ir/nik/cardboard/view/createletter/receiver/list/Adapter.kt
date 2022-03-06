package ir.nik.cardboard.view.createletter.receiver.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nik.cardboard.R
import ir.nik.cardboard.data.network.model.response.CardboardCaseReferralListByWFSCaseIdResponse
import kotlinx.android.synthetic.main.item_letter_receiver_cardboard.view.*

internal class Adapter(
    private val list: List<CardboardCaseReferralListByWFSCaseIdResponse.Result>,
    private val listener: OnActionListener
): RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_letter_receiver_cardboard, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    override fun getItemCount(): Int = list.size

    inner class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBind(model: CardboardCaseReferralListByWFSCaseIdResponse.Result){

            itemView.txtReceiver.text = model.receiverUser
            itemView.setOnClickListener {
                listener.onEdit(model)
            }
            itemView.btnEdit.setOnClickListener {
                listener.onEdit(model)
            }
            itemView.btnDelete.setOnClickListener {
                listener.onDelete(model.wfsCrId ?: 0)
            }
        }
    }

    interface OnActionListener{
        fun onEdit(model: CardboardCaseReferralListByWFSCaseIdResponse.Result)
        fun onDelete(crId: Long)
    }
}