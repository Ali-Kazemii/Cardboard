package ir.nik.cardboard.view.fastrefer

import androidx.lifecycle.MutableLiveData
import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration
import ir.nik.cardboard.data.network.api.CardboardRemoteRepository
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse
import ir.nik.cardboard.data.network.model.request.CardboardDeleteFastReferRequest
import ir.nik.cardboard.data.network.model.request.CardboardFastReferRequest
import ir.nik.cardboard.data.network.model.request.CardboardPostFastReferRequest
import ir.nik.cardboard.data.network.model.request.CardboardResponseId
import ir.nik.cardboard.data.network.model.response.CardboardFastReferResponse
import ir.nik.cardboard.view.base.CardboardBaseViewModel

internal class CardboardFastReferViewModel(
    private val remote: CardboardRemoteRepository,
    pref: CardboardPreferenceConfiguration
) : CardboardBaseViewModel(
    pref = pref,
    remote = remote
) {


    val refersList = MutableLiveData<CardboardFastReferResponse>()


    fun getFastRefers(
        request: CardboardFastReferRequest
    ) {
        remote.getFastRefers(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardFastReferResponse> {
                override fun onDataLoaded(data: CardboardFastReferResponse) {
                    refersList.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun deleteFastRefer(
        request: CardboardDeleteFastReferRequest
    ) {
        remote.deleteFastRefer(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardResponseId> {
                override fun onDataLoaded(data: CardboardResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    errorDelete.postValue(response)
                }
            })
    }


    fun insertUserFastRefer(
        request: CardboardPostFastReferRequest
    ) {
        remote.insertUserFastRefer(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardResponseId> {
                override fun onDataLoaded(data: CardboardResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    response?.let {
                        error.postValue(it)
                    }
                }
            })
    }

    fun updateUserFastRefer(
        request: CardboardPostFastReferRequest
    ) {
        remote.updateUserFastRefer(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardResponseId> {
                override fun onDataLoaded(data: CardboardResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }
}