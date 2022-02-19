package ir.nik.cardboard.view.refer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.convertToBitmap
import kotlinx.android.synthetic.main.item_refer.view.*

internal class Adapter(
    private var list: MutableList<ReferModel>
): RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_refer, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: ReferModel) {
            itemView.txtReceiver.text = model.receiver
            if (model.icon.isNotEmpty())
                itemView.thumbnail.setImageBitmap(convertToBitmap(model.icon))
        }
    }
}