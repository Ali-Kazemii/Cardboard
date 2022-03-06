package ir.nik.cardboard.view.caselist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nik.cardboard.R
import ir.nik.cardboard.data.network.model.response.CardboardInformationResponse
import kotlinx.android.synthetic.main.item_case_list.view.*

internal class CardboardAdapter(
    private val callback: (CardboardInformationResponse.Result, Boolean) -> Unit
) : RecyclerView.Adapter<CardboardAdapter.CustomViewHolder>() {

    private val list: MutableList<CardboardInformationResponse.Result> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setSource(items: MutableList<CardboardInformationResponse.Result>){
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        list.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_case_list, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    fun setSelectableItems(selectable: Boolean) {
        if (selectable) {
            list.forEach {
                it.isSelectable = true
            }

            notifyDataSetChanged()

        } else {
            list.forEach {
                it.isSelectable = false
                it.selected = false
            }
            notifyDataSetChanged()
        }
    }

    fun getSelectedItems(): MutableList<CardboardInformationResponse.Result> {
        return mutableListOf<CardboardInformationResponse.Result>().also {
            list.forEach { model ->
                if (model.selected)
                    it.add(model)
            }
        }
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(model: CardboardInformationResponse.Result) {

            val context = itemView.context

            if (model.wfsCrPreviewDateTime != null) {
                itemView.thumbnail.background =
                    ContextCompat.getDrawable(context, R.drawable.ic_read)
                itemView.cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            } else {
                itemView.thumbnail.background =
                    ContextCompat.getDrawable(context, R.drawable.ic_unread)
                itemView.cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.red_50
                    )
                )
            }
            itemView.txtLetterDate.text = model.requestDate
            itemView.txtLetterTitle.text = model.senderUser
            itemView.txtLetterNum.text = model.requestType
            itemView.txtLetterDescription.text = model.requestSubject
            itemView.setOnClickListener {
                if (model.isSelectable) {
                    if (!model.selected) {
                        model.selected = true
                        itemView.cardView.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.onSurface
                            )
                        )
                    } else {
                        model.selected = false
                        itemView.cardView.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.white
                            )
                        )
                    }

                } else
                    callback.invoke(model, model.wfsCrPreviewDateTime == null)
            }
        }
    }
}