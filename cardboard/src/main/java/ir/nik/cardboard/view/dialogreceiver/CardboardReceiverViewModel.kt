package ir.nik.cardboard.view.dialogreceiver

import androidx.lifecycle.MutableLiveData
import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration
import ir.nik.cardboard.data.network.api.CardboardRemoteRepository
import ir.nik.cardboard.data.network.model.base.BaseGetRequest
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse
import ir.nik.cardboard.data.network.model.response.CardboardReceiverResponse
import ir.nik.cardboard.view.base.CardboardBaseViewModel

internal class CardboardReceiverViewModel(
    pref: CardboardPreferenceConfiguration,
    private val remote: CardboardRemoteRepository
) : CardboardBaseViewModel(pref = pref, remote = remote) {



    val receiverListResponse = MutableLiveData<CardboardReceiverResponse>()

    fun getReceivers(
        request: BaseGetRequest
    ){
        remote.getReceiverList(
            request,
            object: CardboardRemoteRepository.OnApiCallback<CardboardReceiverResponse>{
            override fun onDataLoaded(data: CardboardReceiverResponse) {
                receiverListResponse.postValue(data)
            }

            override fun onError(response: CardboardBaseResponse?) {
                error.postValue(response)
            }
        })
    }
}