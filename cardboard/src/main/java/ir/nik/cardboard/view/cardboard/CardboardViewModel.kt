package ir.nik.cardboard.view.cardboard

import androidx.lifecycle.MutableLiveData
import ir.awlrhm.modules.extentions.formatDate
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.PreferenceConfiguration
import ir.nik.cardboard.data.network.api.RemoteRepository
import ir.nik.cardboard.data.network.model.base.BaseGetRequest
import ir.nik.cardboard.data.network.model.base.BaseResponse
import ir.nik.cardboard.data.network.model.response.CardboardListResponse
import ir.nik.cardboard.view.base.BaseViewModel

internal class CardboardViewModel(
    private val remote: RemoteRepository,
    pref: PreferenceConfiguration,
    calendar: PersianCalendar
) : BaseViewModel(pref, calendar, remote) {

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
            object : RemoteRepository.OnApiCallback<CardboardListResponse> {
                override fun onDataLoaded(data: CardboardListResponse) {
                    cardboardListResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

}