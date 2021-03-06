package ir.nik.cardboard.view.attachment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.convertToBitmap
import ir.nik.cardboard.data.network.model.response.CardboardAttachmentListResponse
import kotlinx.android.synthetic.main.item_attachment_cardboard.view.*

internal class CardboardAdapter(
    private var list: MutableList<CardboardAttachmentListResponse.Result>,
    private val callback: OnActionListener
) : RecyclerView.Adapter<CardboardAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_attachment_cardboard, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: CardboardAttachmentListResponse.Result) {
            itemView.txtFileName.text = model.fileName
            itemView.txtDescription.text = model.description
            itemView.txtRegisterDate.text = model.registerDate
            model.thumbnail?.let {thumbnail ->
                itemView.thumbnail.setImageBitmap(convertToBitmap(thumbnail))
            }
            itemView.layoutDelete.setOnClickListener {
                callback.onDelete(model.daId ?: 0)
            }
            itemView.layoutDownload.setOnClickListener {
                callback.onDownload(model)
            }
        }
    }

    interface OnActionListener{
        fun onDelete(daId: Long)
        fun onDownload(model: CardboardAttachmentListResponse.Result)
    }
}