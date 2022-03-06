package ir.nik.cardboard.view.attachment

import androidx.lifecycle.MutableLiveData
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration
import ir.nik.cardboard.data.network.api.CardboardRemoteRepository
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse
import ir.nik.cardboard.data.network.model.request.CardboardDeleteFileRequest
import ir.nik.cardboard.data.network.model.request.CardboardDocumentAttachmentRequest
import ir.nik.cardboard.data.network.model.request.CardboardPostAttachmentRequest
import ir.nik.cardboard.data.network.model.request.CardboardResponseId
import ir.nik.cardboard.data.network.model.response.CardboardAttachmentListResponse
import ir.nik.cardboard.view.base.CardboardBaseViewModel

internal class CardboardAttachmentViewModel(
    pref: CardboardPreferenceConfiguration,
    calendar: PersianCalendar,
    private val remote: CardboardRemoteRepository,
) : CardboardBaseViewModel(
    pref = pref,
    calendar = calendar
) {

    val listAttachmentResponse = MutableLiveData<CardboardAttachmentListResponse>()
    val downloadError = MutableLiveData<CardboardBaseResponse?>()

    fun getListAttachment(
        request: CardboardDocumentAttachmentRequest
    ) {
        remote.getListAttachment(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardAttachmentListResponse> {
                override fun onDataLoaded(data: CardboardAttachmentListResponse) {
                    listAttachmentResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }


    fun deleteFile(
        request: CardboardDeleteFileRequest
    ) {
        remote.deleteFile(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardResponseId> {
                override fun onDataLoaded(data: CardboardResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    errorDelete.postValue(response)
                }
            }
        )
    }

    fun postAttachment(
        request: CardboardPostAttachmentRequest
    ) {
        remote.postAttachment(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardResponseId> {
                override fun onDataLoaded(data: CardboardResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            }
        )
    }


}



