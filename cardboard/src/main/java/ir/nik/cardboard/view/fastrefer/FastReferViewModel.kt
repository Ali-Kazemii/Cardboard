package ir.nik.cardboard.view.fastrefer

import androidx.lifecycle.MutableLiveData
import ir.nik.cardboard.data.local.PreferenceConfiguration
import ir.nik.cardboard.data.network.api.RemoteRepository
import ir.nik.cardboard.data.network.model.base.BaseResponse
import ir.nik.cardboard.data.network.model.request.DeleteFastReferRequest
import ir.nik.cardboard.data.network.model.request.FastReferRequest
import ir.nik.cardboard.data.network.model.request.PostFastReferRequest
import ir.nik.cardboard.data.network.model.request.ResponseId
import ir.nik.cardboard.data.network.model.response.FastReferResponse
import ir.nik.cardboard.view.base.BaseViewModel

internal class FastReferViewModel(
    private val remote: RemoteRepository,
    pref: PreferenceConfiguration
) : BaseViewModel(
    pref = pref,
    remote = remote
) {


    val refersList = MutableLiveData<FastReferResponse>()


    fun getFastRefers(
        request: FastReferRequest
    ) {
        remote.getFastRefers(
            request,
            object : RemoteRepository.OnApiCallback<FastReferResponse> {
                override fun onDataLoaded(data: FastReferResponse) {
                    refersList.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun deleteFastRefer(
        request: DeleteFastReferRequest
    ) {
        remote.deleteFastRefer(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    errorDelete.postValue(response)
                }
            })
    }


    fun insertUserFastRefer(
        request: PostFastReferRequest
    ) {
        remote.insertUserFastRefer(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let {
                        error.postValue(it)
                    }
                }
            })
    }

    fun updateUserFastRefer(
        request: PostFastReferRequest
    ) {
        remote.updateUserFastRefer(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }
}