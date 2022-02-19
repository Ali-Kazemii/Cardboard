package ir.nik.cardboard.view.dialogreceiver

import androidx.lifecycle.MutableLiveData
import ir.nik.cardboard.data.local.PreferenceConfiguration
import ir.nik.cardboard.data.network.api.RemoteRepository
import ir.nik.cardboard.data.network.model.base.BaseGetRequest
import ir.nik.cardboard.data.network.model.base.BaseResponse
import ir.nik.cardboard.data.network.model.response.ReceiverResponse
import ir.nik.cardboard.view.base.BaseViewModel

internal class ReceiverViewModel(
    pref: PreferenceConfiguration,
    private val remote: RemoteRepository
) : BaseViewModel(pref = pref, remote = remote) {



    val receiverListResponse = MutableLiveData<ReceiverResponse>()

    fun getReceivers(
        request: BaseGetRequest
    ){
        remote.getReceiverList(
            request,
            object: RemoteRepository.OnApiCallback<ReceiverResponse>{
            override fun onDataLoaded(data: ReceiverResponse) {
                receiverListResponse.postValue(data)
            }

            override fun onError(response: BaseResponse?) {
                error.postValue(response)
            }
        })
    }
}