package ir.nik.cardboard.view.refer

import androidx.lifecycle.MutableLiveData
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration
import ir.nik.cardboard.data.network.api.CardboardRemoteRepository
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse
import ir.nik.cardboard.data.network.model.request.CardboardProcessStepRequest
import ir.nik.cardboard.data.network.model.response.CardboardProcessStepResponse
import ir.nik.cardboard.view.base.CardboardBaseViewModel

internal class CardboardReferViewModel(
    private val remote: CardboardRemoteRepository,
    pref: CardboardPreferenceConfiguration,
    calendar: PersianCalendar
) : CardboardBaseViewModel(
    pref = pref,
    calendar = calendar,
    remote = remote
) {

    val processStepResponse = MutableLiveData<CardboardProcessStepResponse>()

    fun getProcessStepList(
        request: CardboardProcessStepRequest
    ) {
        remote.getProcessStepList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardProcessStepResponse> {
                override fun onDataLoaded(data: CardboardProcessStepResponse) {
                    processStepResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }
}