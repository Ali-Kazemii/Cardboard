package ir.nik.cardboard.view.dialogreceiver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.convertToBitmap
import ir.nik.cardboard.data.network.model.response.ReceiverResponse
import kotlinx.android.synthetic.main.item_receiver.view.*

internal class ReceiverAdapter(
    val list: MutableList<ReceiverResponse.Result>,
    val callback: (ReceiverResponse.Result) -> Unit
) : RecyclerView.Adapter<ReceiverAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_receiver, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: ReceiverResponse.Result) {
            itemView.txtReceiver.text = model.userName

            model.thumbnail?.let {
                Glide
                    .with(itemView.context)
                    .load(convertToBitmap(it))
                    .centerCrop()
                    .apply(RequestOptions())
                    .error(R.drawable.icon_account)
                    .into(itemView.thumbnail)
            }
            itemView.setOnClickListener {
                callback.invoke(model)
            }
        }
    }
}