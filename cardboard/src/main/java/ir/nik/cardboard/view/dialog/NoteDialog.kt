package ir.nik.cardboard.view.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nik.cardboard.R
import ir.nik.cardboard.utils.Const
import kotlinx.android.synthetic.main.dialog_note.*

class NoteDialog(
    private val callback: (String) -> Unit
) : DialogFragment() {

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val activity = activity ?: return

        dialog.setContentView(R.layout.dialog_note)
        dialog.layoutOk.setOnClickListener {
            if (dialog.edtNote.text.toString().isNotEmpty()) {
                callback.invoke(dialog.edtNote.text.toString())
                dismiss()

            }else
                dialog.tilNote.error = activity.getString(R.string.fill_note)
        }
        dialog.layoutCancel.setOnClickListener {
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

    companion object {
        val TAG = "${Const.APP_NAME}: ${NoteDialog::class.java.simpleName}"
    }
}