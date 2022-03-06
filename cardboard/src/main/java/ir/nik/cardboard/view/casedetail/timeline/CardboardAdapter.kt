package ir.nik.cardboard.view.casedetail.timeline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nik.cardboard.R
import ir.nik.cardboard.view.casedetail.timeline.CardboardViewType.Companion.CREATOR
import ir.nik.cardboard.view.casedetail.timeline.CardboardViewType.Companion.ITEM
import ir.nik.cardboard.view.casedetail.timeline.CardboardViewType.Companion.SENDER
import ir.nik.cardboard.view.casedetail.timeline.CardboardViewType.Companion.SPECIFIC
import kotlinx.android.synthetic.main.item_case_history_cardboard.view.*
import pl.hypeapp.materialtimelineview.MaterialTimelineView

internal class CardboardAdapter(private val list:MutableList<CardboardViewType>)
    : RecyclerView.Adapter<CardboardAdapter.TimelineViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return list[position].getViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        return when (viewType) {
            CardboardViewType.LINE -> TimelineViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.timeline_line_cardboard, parent, false))
            else -> TimelineViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_case_history_cardboard, parent, false))
        }
    }

    override fun getItemCount(): Int= list.size

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val model = list[position]
        if(model.getViewType() == ITEM)
            holder.bindItem(model)
        else
            holder.bindLine(model as CardboardLineModel)
    }

    inner class TimelineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItem(model: CardboardViewType){

            when(model.getTimelineType()){
                SPECIFIC -> {
                    val model = model as CardboardTypeSpecific
                    itemView.layoutUserProfile.visibility = View.GONE
                    itemView.layoutCreator.visibility = View.GONE

                    itemView.txtSender.text = model.sender
                    itemView.txtReceiver.text = model.receiver
                    itemView.txtReferDate.text = model.referDate
                    itemView.txtReferDescription.text = model.referDescription
                }
                SENDER -> {
                    val model = model as CardboardTypeSender
                    itemView.layoutCreator.visibility = View.GONE
                    itemView.layoutSpecific.visibility = View.GONE

                    itemView.txtLetterSender.text = model.sender
                }
                CREATOR ->{
                    val model = model as CardboardTypeCreator
                    itemView.layoutUserProfile.visibility = View.GONE
                    itemView.layoutSpecific.visibility = View.GONE

                    itemView.txtLetterCreator.text = model.creator
                }
            }

            when {
                model.isHeader -> itemView.itemStep.position = MaterialTimelineView.POSITION_FIRST
                model.isFooter -> itemView.itemStep.position = MaterialTimelineView.POSITION_LAST
                else -> itemView.itemStep.position = MaterialTimelineView.POSITION_MIDDLE
            }
        }

        fun bindLine(model: CardboardLineModel){}
    }
}