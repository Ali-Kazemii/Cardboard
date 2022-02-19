package ir.nik.cardboard.view.refer

import androidx.lifecycle.MutableLiveData
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.PreferenceConfiguration
import ir.nik.cardboard.data.network.api.RemoteRepository
import ir.nik.cardboard.data.network.model.base.BaseResponse
import ir.nik.cardboard.data.network.model.request.ProcessStepRequest
import ir.nik.cardboard.data.network.model.response.ProcessStepResponse
import ir.nik.cardboard.view.base.BaseViewModel

internal class ReferViewModel(
    private val remote: RemoteRepository,
    pref: PreferenceConfiguration,
    calendar: PersianCalendar
) : BaseViewModel(
    pref = pref,
    calendar = calendar,
    remote = remote
) {

    val processStepResponse = MutableLiveData<ProcessStepResponse>()

    fun getProcessStepList(
        request: ProcessStepRequest
    ) {
        remote.getProcessStepList(
            request,
            object : RemoteRepository.OnApiCallback<ProcessStepResponse> {
                override fun onDataLoaded(data: ProcessStepResponse) {
                    processStepResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }
}