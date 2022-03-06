package ir.nik.cardboard.view.caselist

import androidx.lifecycle.MutableLiveData
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration
import ir.nik.cardboard.data.network.api.CardboardRemoteRepository
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse
import ir.nik.cardboard.data.network.model.request.CardboardInformationRequest
import ir.nik.cardboard.data.network.model.response.CardboardInformationResponse
import ir.nik.cardboard.view.base.CardboardBaseViewModel

internal class CardboardCaseListViewModel(
    private val remote: CardboardRemoteRepository,
    pref: CardboardPreferenceConfiguration,
    calendar: PersianCalendar
) : CardboardBaseViewModel(
    pref = pref,
    calendar = calendar,
    remote = remote
) {

    val listCaseResponse = MutableLiveData<CardboardInformationResponse>()


    fun getCardboardInformationList(
        request: CardboardInformationRequest
    ) {
        remote.getCardboardInformationList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardInformationResponse> {
                override fun onDataLoaded(data: CardboardInformationResponse) {
                    listCaseResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            }
        )
    }
}
