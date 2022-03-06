package ir.nik.cardboard.view.createletter.draft

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nik.cardboard.R
import ir.nik.cardboard.data.network.model.response.CardboardDraftLetterResponse
import kotlinx.android.synthetic.main.item_letter_draft.view.*

internal class Adapter(
    private val list: List<CardboardDraftLetterResponse.Result>,
    private val listener: OnActionListener
) : RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_letter_draft, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    override fun getItemCount(): Int = list.size

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: CardboardDraftLetterResponse.Result) {

            arrangeTitle(model.letterTitle)?.let {
                itemView.txtDraft.text = it
            }
            itemView.setOnClickListener {
                listener.onEdit(model)
            }
            itemView.layoutDelete.setOnClickListener {
                listener.onDelete(model.letterId ?: 0)
            }
            itemView.layoutLetterInformation.setOnClickListener {
                listener.onLetterInformation(model.letterId ?: 0)
            }
            itemView.layoutAttachment.setOnClickListener {
                listener.onAttachment(model.letterId ?: 0)
            }
            itemView.layoutReceiver.setOnClickListener {
                listener.onReceivers(model.wfsCaseId ?: 0)
            }
            itemView.layoutLetterLinked.setOnClickListener {
                listener.onLetterLinked(model.wfsCaseId ?: 0)
            }
            itemView.layoutExtraInformation.setOnClickListener {
                listener.onLetterExtraInformation(model.letterId ?: 0, model.customerId ?: 0)
            }
            itemView.layoutSend.setOnClickListener {
                listener.onLetterSend(model.letterId ?: 0)
            }
        }

        private fun arrangeTitle(letterTitle: String?): String? {
            letterTitle?.let {
                val str = letterTitle.split(',')
                val title = StringBuilder()
                str.forEachIndexed { index, item ->
                    title.append(item)
                    if (index + 1 != list.size)
                        title.append("\n")
                }
                return title.toString()
            } ?: kotlin.run {
                return null
            }
        }
    }

    interface OnActionListener {
        fun onEdit(model: CardboardDraftLetterResponse.Result)
        fun onDelete(letterId: Long)
        fun onLetterInformation(letterId: Long)
        fun onAttachment(letterId: Long)
        fun onReceivers(wfsCaseId: Long)
        fun onLetterLinked(wfsCaseId: Long)
        fun onLetterExtraInformation(letterId: Long, customerId: Long)
        fun onLetterSend(letterId: Long)
    }
}