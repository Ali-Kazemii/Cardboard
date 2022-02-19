package ir.nik.cardboard.view.createletter.step

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nik.cardboard.R
import kotlinx.android.synthetic.main.item_letter_step.view.*

internal class Adapter(
    private val list: List<StepModel>,
    private val callback: (String)-> Unit
): RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_letter_step, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: StepModel) {
            val context = itemView.context

            itemView.txtStep.text = model.title
            itemView.txtStatus.text = model.description
            if(model.status != 0)
                itemView.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.complete))
            else
                itemView.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.not_complete))

            Glide.with(context)
                .load(model.icon)
                .apply(RequestOptions())
                .into(itemView.imgStep)

            itemView.setOnClickListener {
                callback.invoke(model.id)
            }
        }
    }
}