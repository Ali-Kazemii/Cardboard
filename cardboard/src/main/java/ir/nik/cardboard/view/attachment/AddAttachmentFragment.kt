package ir.nik.cardboard.view.attachment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.*
import ir.nik.cardboard.data.network.model.request.PostAttachmentRequest
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.PATH_STORAGE
import ir.nik.cardboard.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_attachment.*
import kotlinx.android.synthetic.main.item_attachment_delete.view.*
import kotlinx.android.synthetic.main.item_attachment_image.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

internal class AddAttachmentFragment(
    private val relatedTableId: Long,
    private val relatedTableName: String,
    private val dcId: Long
) : BaseFragment() {

    private val viewModel by viewModel<AttachmentViewModel>()
    private val listAttachments: ArrayList<MediaFile> = arrayListOf()

    override fun setup() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_attachment, container, false)
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return

        btnBack.setOnClickListener { activity.onBackPressed() }
        btnAdd.setOnClickListener {
            activity.checkReadWriteStoragePermission {
                chooseFile()
            }
        }
        btnUpload.setOnClickListener {
            postAttachments()
        }
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.responseId.observe(viewLifecycleOwner, { response ->
            loading(false)
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let {
                    if (it != 0L) {
                        activity.successOperation(response.message)
                        activity.onBackPressed()

                    } else
                        activity.showError(response.message)
                }?: kotlin.run {
                    activity.showError(response.message)
                }
            }
        })
    }

    private fun chooseFile() {
        val activity = activity ?: return

        val intent = Intent(activity, FilePickerActivity::class.java)
        intent.putExtra(
            FilePickerActivity.CONFIGS, Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .setShowFiles(true)
                .enableImageCapture(true)
                .setMaxSelection(10)
                .setSkipZeroSizeFiles(true)
                .setSuffixes(
                    "txt",
                    "pdf",
                    "zip",
                    "tar",
                    "gz",
                    "rar",
                    "7z",
                    "doc",
                    "docx,\"xls\", \"xlsx"
                )
                .build()
        )
        startActivityForResult(intent, KEY_PICK_FILE)
    }

    private fun createAttachments(attachments: ArrayList<MediaFile>) {
        noData.isVisible = false
        attachments.forEach { item ->
            val view =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.item_attachment_delete, layoutAttachment, false)
            view.txtFileName.text = item.name
            view.txtFileSize.text = getFileSize(item.size)
            view.btnDelete.setOnClickListener {
                listAttachments.remove(item)
                layoutAttachment.removeView(view)
                if (layoutAttachment.childCount == 0)
                    noData.isVisible = true
            }
            Glide.with(this)
                .load(Uri.fromFile(File(item.path)))
                .apply(RequestOptions())
                .into(view.thumbnail)
            layoutAttachment.addView(view)
        }
    }


    private fun createImageEdited(fileName: String) {
        val path = PATH_STORAGE + File.separator + fileName
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.item_attachment_image, layoutAttachment, false)
        view?.let {
            it.txtImageName.text = fileName
            Glide.with(this)
                .load(Uri.fromFile(File(path)))
                .apply(RequestOptions())
                .into(it.imgAttachment)
            layoutAttachment.addView(it)
        }
    }

    private fun getFileSize(length: Long) = "${length / 1024}/${length % 1024}"

    private fun postAttachments() {
        if (isValid) {
            loading(true)
            listAttachments.forEach { item ->
                viewModel.postAttachment(
                    PostAttachmentRequest().also { request ->
                        request.dvId = 1
                        request.dcId = dcId
                        request.detId = 0
                        request.datId = 0
                        request.fileSize = item.size
                        request.fileName = item.name
                        request.registerDate = viewModel.currentDate
                        request.documentRelatedTableId = relatedTableId
                        request.financialYearId = viewModel.financialYear
                        request.documentRelatedTableName = relatedTableName
                        request.userId = viewModel.userId
                        request.body = convertToBase64(File(item.path))
                    })
            }
        } else
            activity?.yToast(getString(R.string.no_file_exist_to_upload), MessageStatus.ERROR)
    }

    private fun loading(visible: Boolean) {
        loading.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private val isValid: Boolean
        get() {
            return layoutAttachment.childCount != 0
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null)
            when (requestCode) {
                KEY_PICK_FILE -> {
                    val files: ArrayList<MediaFile>? =
                        data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
                    files?.let { list ->
                        listAttachments.addAll(list)
                        createAttachments(list)
                    }
                }
                KEY_EDIT_IMAGE -> createImageEdited(data.getStringExtra("fileName") ?: "")
            }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                loading(false)
                activity?.showError(it?.message)
            }
        })
    }

    companion object {
        const val KEY_PICK_FILE = 11222
        const val KEY_EDIT_IMAGE = 11223
        val TAG = "${Const.APP_NAME}: ${AddAttachmentFragment::class.java.simpleName}"
    }
}