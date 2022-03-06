package ir.nik.cardboard.view.cardboard

import androidx.lifecycle.MutableLiveData
import ir.awlrhm.modules.extentions.formatDate
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration
import ir.nik.cardboard.data.network.api.CardboardRemoteRepository
import ir.nik.cardboard.data.network.model.base.BaseGetRequest
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse
import ir.nik.cardboard.data.network.model.response.CardboardListResponse
import ir.nik.cardboard.view.base.CardboardBaseViewModel

internal class CardboardViewModel(
    private val remote: CardboardRemoteRepository,
    pref: CardboardPreferenceConfiguration,
    calendar: PersianCalendar
) : CardboardBaseViewModel(pref, calendar, remote) {

    val cardboardListResponse = MutableLiveData<CardboardListResponse>()



    fun initDefaultDate() {
        val stDate = currentDate.split('/')
        if (stDate[1].toInt() > 2)
            documentStartDate =
                formatDate("${stDate[0]}/${stDate[1].toInt() - 2}/01")
        else
            documentStartDate = startDate
        documentEndDate = currentDate
    }

    fun getCardboardList(
        request: BaseGetRequest
    ) {
        remote.getCardboardList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardListResponse> {
                override fun onDataLoaded(data: CardboardListResponse) {
                    cardboardListResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }

}