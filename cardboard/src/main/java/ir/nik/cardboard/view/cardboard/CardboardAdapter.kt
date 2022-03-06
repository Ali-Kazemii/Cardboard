package ir.nik.cardboard.view.cardboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.convertToBitmap
import ir.nik.cardboard.data.network.model.response.CardboardListResponse
import kotlinx.android.synthetic.main.item_cardboard_cardboard.view.*

internal class CardboardAdapter(
    private val list: MutableList<CardboardListResponse.Result>,
    private val callback:(id: Long, name: String?) -> Unit): RecyclerView.Adapter<CardboardAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardboard_cardboard, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItem(model: CardboardListResponse.Result){
            model.iconUrl?.let {
                Glide
                    .with(itemView.context)
                    .load(convertToBitmap(model.iconUrl))
                    .apply(RequestOptions())
                    .centerCrop()
                    .error(R.drawable.icon_account)
                    .into( itemView.imgIcon)
            }
            itemView.txtName.text = model.name
            itemView.txtCount.text = model.totalCount.toString()
            itemView.setOnClickListener {
                    callback.invoke(
                        model.id ?: 0 ,model.name
                    )
                }
            }
        }
}