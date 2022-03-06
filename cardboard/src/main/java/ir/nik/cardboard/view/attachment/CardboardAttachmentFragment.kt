package ir.nik.cardboard.view.attachment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.*
import ir.awlrhm.modules.view.ActionDialog
import ir.nik.cardboard.data.network.model.request.CardboardDeleteFileRequest
import ir.nik.cardboard.data.network.model.request.CardboardDocumentAttachmentRequest
import ir.nik.cardboard.data.network.model.response.CardboardAttachmentListResponse
import ir.nik.cardboard.utils.APP_NAME_FARSI
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.PATH_STORAGE
import ir.nik.cardboard.utils.attachmentJson
import ir.nik.cardboard.view.base.CardboardBaseFragment
import kotlinx.android.synthetic.main.fragment_attachment_cardboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CardboardAttachmentFragment(
    private val dcId: Long,
    private val relatedTableId: Long,
    private val listener: OnActionListener
) : CardboardBaseFragment() {

    private val viewModel by viewModel<CardboardAttachmentViewModel>()
    private val pageNumber: Int = 1
    private var isOnDownload: Boolean = false

    override fun setup() {
        rclAttachment.layoutManager(LinearLayoutManager(activity))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_attachment_cardboard, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (!rclAttachment.isOnLoading)
            rclAttachment.showLoading()
        getAttachments()
    }

    override fun handleOnClickListeners() {
        btnBack.setOnClickListener { activity?.onBackPressed() }
        btnAdd.setOnClickListener { listener.onAddClick() }
    }

    override fun handleObservers() {
        val activity = activity ?: return
        viewModel.listAttachmentResponse.observe(viewLifecycleOwner, {
            if (rclAttachment.actionLoading)
                rclAttachment.actionLoading = false
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { list ->
                    if (isOnDownload) {
                        isOnDownload = false
                        activity.checkWriteStoragePermission {
                            saveAttachment(list[0])
                        }
                    } else
                        setAdapter(list)

                } ?: kotlin.run {
                    if (isOnDownload) {
                        activity.showError(
                            it.message ?: getString(R.string.error_download_file_call_support)
                        )
                    } else
                        rclAttachment.showNoData()
                }
            }
        })

        viewModel.responseId.observe(viewLifecycleOwner, {
            rclAttachment.actionLoading = false
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { result ->
                    if (result != 0L) {
                        activity.successOperation(it.message)
                        rclAttachment.showLoading()
                        getAttachments()

                    } else
                        activity.showError(it.message)
                } ?: kotlin.run {
                    activity.showError(it.message)
                }
            }
        })
    }


    private fun getAttachments() {
        viewModel.getListAttachment(
            CardboardDocumentAttachmentRequest().also { request ->
                request.jsonParameters = attachmentJson(relatedTableId, dcId)
                request.pageNumber = pageNumber
                request.userId = viewModel.userId
                request.typeOperation = 101
            }
        )
    }


    private fun setAdapter(list: MutableList<CardboardAttachmentListResponse.Result>) {
        val activity = activity ?: return

        if (list.isEmpty()) {
            rclAttachment.showNoData()

        } else {
            rclAttachment.view?.adapter = CardboardAdapter(
                list,
                object : CardboardAdapter.OnActionListener {
                    override fun onDelete(daId: Long) {
                        ActionDialog.Builder()
                            .setTitle(getString(R.string.warning))
                            .setDescription(getString(R.string.are_you_sure_delete))
                            .setPositive(getString(R.string.yes)) {
                                rclAttachment.actionLoading = true
                                viewModel.deleteFile(
                                    CardboardDeleteFileRequest().apply {
                                        this.daId = daId
                                    }
                                )
                            }
                            .setNegative(getString(R.string.no)) {}
                            .build()
                            .show(activity.supportFragmentManager, ActionDialog.TAG)
                    }

                    override fun onDownload(model: CardboardAttachmentListResponse.Result) {
                        isOnDownload = true
                        rclAttachment.actionLoading = true
                        //download attachment service
                        viewModel.getListAttachment(
                            CardboardDocumentAttachmentRequest().also { request ->
                                request.daId = model.daId ?: 0
                                request.userId = viewModel.userId
                                request.typeOperation = 501
                            }
                        )
                    }
                })
        }
    }


    private fun saveAttachment(result: CardboardAttachmentListResponse.Result) {
        val activity = activity ?: return
        if (
            result.documentBody != null
            && result.extensionType != null
            && result.documentName != null
        ) {
            convertBase64ToFile(
                result.documentBody,
                PATH_STORAGE,
                result.documentName,
                result.extensionType
            ) {
                if (it)
                    activity.ySnake(
                        getString(R.string.path_saved_file).replace("$", APP_NAME_FARSI),
                        getString(R.string.understand)
                    )
                else
                    activity.showFlashbar(
                        "",
                        getString(R.string.error_save_file_call_support),
                        MessageStatus.ERROR
                    )
            }
        } else
            activity.showFlashbar(
                getString(R.string.error_save_file),
                getString(R.string.incomplete_file_call_support),
                MessageStatus.ERROR
            )
    }


    override fun handleError() {
        super.handleError()
        val activity = activity ?: return

        viewModel.error.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                rclAttachment.actionLoading = false
                rclAttachment.showNoData()
                activity.showError(it?.message)
            }
        })
        viewModel.downloadError.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                rclAttachment.actionLoading = false
                rclAttachment.showNoData()
                activity.showError(it?.message)
            }
        })
        viewModel.errorDelete.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                rclAttachment.actionLoading = false
                activity.showError(it?.message)
            }
        })
    }

    interface OnActionListener {
        fun onAddClick()
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${CardboardAttachmentFragment::class.java.simpleName}"
    }
}