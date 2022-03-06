package ir.nik.cardboard.view.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.formatDate
import ir.awlrhm.modules.extentions.showDateDialog
import ir.awlrhm.modules.models.ItemModel
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.view.caselist.CardboardCaseListViewModel
import kotlinx.android.synthetic.main.dialog_case_filter_cardboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CardboardCaseFilterDialog(
    private val listSubjects: MutableList<ItemModel>,
    private val callback: (Long) -> Unit
) : DialogFragment() {

    private val viewModel by viewModel<CardboardCaseListViewModel>()
    private var subjectId: Long = 0

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        dialog.setContentView(R.layout.dialog_case_filter_cardboard)
        val activity = activity ?: return

        dialog.dvFrom.date = viewModel.documentStartDate
        dialog.dvTo.date = viewModel.documentEndDate

        if (listSubjects.size == 0)
            dialog.spAccessType.visibility = View.GONE
        else
            dialog.spAccessType.setOnClickListener {
                dialog.spAccessType.loading(true)
                showSubjectsList()
            }

        dialog.dvTo.setOnClickListener {
            activity.showDateDialog {
                val date = formatDate(it)
                dialog.dvTo.date = date
                viewModel.documentEndDate = date
            }
        }

        dialog.dvFrom.setOnClickListener {
            activity.showDateDialog {
                val date = formatDate(it)
                dialog.dvFrom.date = date
                viewModel.documentStartDate = date
            }
        }

        dialog.layoutDone.setOnClickListener {
            callback.invoke(subjectId)
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun showSubjectsList() {
        val activity = activity ?: return
        val dialog = dialog ?: return

        if (listSubjects.size != 0)
            dialog.spAccessType.showData(listSubjects, activity) {
                subjectId = it.id
            }
        else
            dialog.spAccessType.loading(false)
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${CardboardCaseFilterDialog::class.java.simpleName}"
    }
}