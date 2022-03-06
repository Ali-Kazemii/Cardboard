package ir.nik.cardboard.view.createletter.linked.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nik.cardboard.R
import ir.nik.cardboard.data.network.model.response.CardboardCaseListLinkedResponse
import kotlinx.android.synthetic.main.item_letter_linked.view.*

internal class Adapter(
    private val list: List<CardboardCaseListLinkedResponse.Result>,
    private val listener: OnActionListener
): RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_letter_linked, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    override fun getItemCount(): Int = list.size

    inner class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBind(model: CardboardCaseListLinkedResponse.Result){
            arrangeTitle(model.title)?.let {
                itemView.txtTitle.text = it
            }
            itemView.btnDelete.setOnClickListener {
                listener.onDelete(model.clId ?: 0)
            }
        }

        private fun arrangeTitle(letterTitle: String?): String? {
            letterTitle?.let {
                val str = letterTitle.split(',')
                val title = StringBuilder()
                str.forEachIndexed { index, str ->
                    title.append(str)
                    if (index + 1 != list.size)
                        title.append("\n")
                }
                return title.toString()
            } ?: kotlin.run {
                return null
            }
        }
    }

    interface OnActionListener{
        fun onDelete(clId: Long)
    }
}