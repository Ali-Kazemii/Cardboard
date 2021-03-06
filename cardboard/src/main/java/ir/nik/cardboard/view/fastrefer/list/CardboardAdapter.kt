package ir.nik.cardboard.view.fastrefer.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nik.cardboard.R
import ir.nik.cardboard.data.network.model.response.CardboardFastReferResponse
import kotlinx.android.synthetic.main.item_fast_refer_cardboard.view.*

internal class CardboardAdapter(
    private val list: MutableList<CardboardFastReferResponse.Result>,
    private val listener: OnActionListener
) : RecyclerView.Adapter<CardboardAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_fast_refer_cardboard, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: CardboardFastReferResponse.Result) {
            itemView.txtTitle.text = model.title?.trim()
            itemView.txtReferDescription.text = model.description
            itemView.txtReceiver.text = model.userReceiverFullName
            itemView.txtReferType.text = model.wfsRtName
            itemView.txtReceivedType.text = model.wfsDtName
            itemView.txtReferNature.text = model.wfsNrName
            itemView.btnDelete.setOnClickListener {
                listener.onDelete(model.ufrId ?: 0)
            }
            itemView.btnEdit.setOnClickListener {
                listener.onEdit(model)
            }
        }
    }

    interface OnActionListener {
        fun onDelete(ufrId: Long)
        fun onEdit(model: CardboardFastReferResponse.Result)
    }
}