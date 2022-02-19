package ir.nik.cardboard.view.caselist

import androidx.lifecycle.MutableLiveData
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.PreferenceConfiguration
import ir.nik.cardboard.data.network.api.RemoteRepository
import ir.nik.cardboard.data.network.model.base.BaseResponse
import ir.nik.cardboard.data.network.model.request.CardboardInformationRequest
import ir.nik.cardboard.data.network.model.response.CardboardInformationResponse
import ir.nik.cardboard.view.base.BaseViewModel

internal class CaseListViewModel(
    private val remote: RemoteRepository,
    pref: PreferenceConfiguration,
    calendar: PersianCalendar
) : BaseViewModel(
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
            object : RemoteRepository.OnApiCallback<CardboardInformationResponse> {
                override fun onDataLoaded(data: CardboardInformationResponse) {
                    listCaseResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            }
        )
    }
}
