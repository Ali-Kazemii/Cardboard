package ir.nik.cardboard.view.casedetail

import androidx.lifecycle.MutableLiveData
import com.nik.cardboard.R
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.PreferenceConfiguration
import ir.nik.cardboard.data.network.api.RemoteRepository
import ir.nik.cardboard.data.network.model.base.BaseResponse
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.response.CaseDetailInformationResponse
import ir.nik.cardboard.data.network.model.response.CaseReferHistoryResponse
import ir.nik.cardboard.data.network.model.response.ControlValidationResponse
import ir.nik.cardboard.data.network.model.response.DocumentReportResponse
import ir.nik.cardboard.view.base.BaseViewModel
import ir.nik.cardboard.view.casedetail.timeline.LineModel
import ir.nik.cardboard.view.casedetail.timeline.TypeCreator
import ir.nik.cardboard.view.casedetail.timeline.TypeSpecific
import ir.nik.cardboard.view.casedetail.timeline.ViewType

internal class CaseDetailViewModel(
    private val remote: RemoteRepository,
    pref: PreferenceConfiguration,
    calendar: PersianCalendar
) : BaseViewModel(
    pref = pref,
    calendar = calendar,
    remote = remote
) {

    val listHistory = MutableLiveData<MutableList<ViewType>>()
    val caseInformationResponse = MutableLiveData<CaseDetailInformationResponse>()
    val reportBody = MutableLiveData<DocumentReportResponse>()
    val controlValidationResponse = MutableLiveData<ControlValidationResponse>()
    val errorBody = MutableLiveData<BaseResponse?>()

    fun getCaseDetailInformation(
        request: CaseDetailInformationRequest
    ) {
        remote.getCaseDetailInformation(
            request,
            object : RemoteRepository.OnApiCallback<CaseDetailInformationResponse> {
                override fun onDataLoaded(data: CaseDetailInformationResponse) {
                    caseInformationResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getCaseDetailBody(
        request: CaseDetailBodyRequest
    ) {
        remote.getCaseDetailBody(
            request,
            object : RemoteRepository.OnApiCallback<DocumentReportResponse> {
                override fun onDataLoaded(data: DocumentReportResponse) {
                    reportBody.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    errorBody.postValue(response)
                }
            })
    }

    fun getCaseReferHistory(
        request: CaseReferHistoryRequest
    ) {
        remote.getCaseReferHistory(
            request,
            object : RemoteRepository.OnApiCallback<CaseReferHistoryResponse> {
                override fun onDataLoaded(data: CaseReferHistoryResponse) {
                    data.result?.let { list ->
                        if (list.isNotEmpty())
                            getCaseHistory(list)
                    }
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getCaseHistory(list: List<CaseReferHistoryResponse.Result>) {
        val listViews = mutableListOf<ViewType>()

        val header = list[0]
        val item1 = TypeCreator(header.registerUser ?: "")
        item1.isHeader = true
        listViews.add(item1)

        listViews.add(LineModel())

        for (i in list.indices) {
            val data = list[i]
            val item = TypeSpecific(
                data.senderUserName ?: "",
                data.receiverUser ?: "",
                data.wfsCrDateTime ?: "",
                data.referDescription ?: ""
            )
            if (i < list.size - 1) {
                item.isFooter = false
                listViews.add(item)
                listViews.add(LineModel())
            } else {
                item.isFooter = true
                listViews.add(item)
            }
        }

        listHistory.postValue(listViews)
    }

    fun postTransferToFolder(
        request: TransferToFolderRequest,
    ) {
        remote.postTransferToFolder(
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

    fun callControlValidation(
        request: ControlValidationRequest
    ) {
        remote.callControlValidation(
            request,
            object : RemoteRepository.OnApiCallback<ControlValidationResponse> {
                override fun onDataLoaded(data: ControlValidationResponse) {
                    controlValidationResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }
}