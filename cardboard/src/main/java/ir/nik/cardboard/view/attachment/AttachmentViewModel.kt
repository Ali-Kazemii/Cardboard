package ir.nik.cardboard.view.attachment

import androidx.lifecycle.MutableLiveData
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.PreferenceConfiguration
import ir.nik.cardboard.data.network.api.RemoteRepository
import ir.nik.cardboard.data.network.model.base.BaseResponse
import ir.nik.cardboard.data.network.model.request.DeleteFileRequest
import ir.nik.cardboard.data.network.model.request.DocumentAttachmentRequest
import ir.nik.cardboard.data.network.model.request.PostAttachmentRequest
import ir.nik.cardboard.data.network.model.request.ResponseId
import ir.nik.cardboard.data.network.model.response.AttachmentListResponse
import ir.nik.cardboard.view.base.BaseViewModel

internal class AttachmentViewModel(
    pref: PreferenceConfiguration,
    calendar: PersianCalendar,
    private val remote: RemoteRepository,
) : BaseViewModel(
    pref = pref,
    calendar = calendar
) {

    val listAttachmentResponse = MutableLiveData<AttachmentListResponse>()
    val downloadError = MutableLiveData<BaseResponse?>()

    fun getListAttachment(
        request: DocumentAttachmentRequest
    ) {
        remote.getListAttachment(
            request,
            object : RemoteRepository.OnApiCallback<AttachmentListResponse> {
                override fun onDataLoaded(data: AttachmentListResponse) {
                    listAttachmentResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }


    fun deleteFile(
        request: DeleteFileRequest
    ) {
        remote.deleteFile(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    errorDelete.postValue(response)
                }
            }
        )
    }

    fun postAttachment(
        request: PostAttachmentRequest
    ) {
        remote.postAttachment(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            }
        )
    }


}



