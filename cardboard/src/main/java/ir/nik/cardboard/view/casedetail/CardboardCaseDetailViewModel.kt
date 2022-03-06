package ir.nik.cardboard.view.casedetail

import androidx.lifecycle.MutableLiveData
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration
import ir.nik.cardboard.data.network.api.CardboardRemoteRepository
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.response.CardboardCaseDetailInformationResponse
import ir.nik.cardboard.data.network.model.response.CardboardCaseReferHistoryResponse
import ir.nik.cardboard.data.network.model.response.CardboardControlValidationResponse
import ir.nik.cardboard.data.network.model.response.CardboardDocumentReportResponse
import ir.nik.cardboard.view.base.CardboardBaseViewModel
import ir.nik.cardboard.view.casedetail.timeline.CardboardLineModel
import ir.nik.cardboard.view.casedetail.timeline.CardboardTypeCreator
import ir.nik.cardboard.view.casedetail.timeline.CardboardTypeSpecific
import ir.nik.cardboard.view.casedetail.timeline.CardboardViewType

internal class CardboardCaseDetailViewModel(
    private val remote: CardboardRemoteRepository,
    pref: CardboardPreferenceConfiguration,
    calendar: PersianCalendar
) : CardboardBaseViewModel(
    pref = pref,
    calendar = calendar,
    remote = remote
) {

    val listHistory = MutableLiveData<MutableList<CardboardViewType>>()
    val caseInformationResponse = MutableLiveData<CardboardCaseDetailInformationResponse>()
    val reportBody = MutableLiveData<CardboardDocumentReportResponse>()
    val controlValidationResponse = MutableLiveData<CardboardControlValidationResponse>()
    val errorBody = MutableLiveData<CardboardBaseResponse?>()

    fun getCaseDetailInformation(
        request: CardboardCaseDetailInformationRequest
    ) {
        remote.getCaseDetailInformation(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardCaseDetailInformationResponse> {
                override fun onDataLoaded(data: CardboardCaseDetailInformationResponse) {
                    caseInformationResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getCaseDetailBody(
        request: CardboardCaseDetailBodyRequest
    ) {
        remote.getCaseDetailBody(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardDocumentReportResponse> {
                override fun onDataLoaded(data: CardboardDocumentReportResponse) {
                    reportBody.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    errorBody.postValue(response)
                }
            })
    }

    fun getCaseReferHistory(
        request: CardboardCaseReferHistoryRequest
    ) {
        remote.getCaseReferHistory(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardCaseReferHistoryResponse> {
                override fun onDataLoaded(data: CardboardCaseReferHistoryResponse) {
                    data.result?.let { list ->
                        if (list.isNotEmpty())
                            getCaseHistory(list)
                    }
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getCaseHistory(list: List<CardboardCaseReferHistoryResponse.Result>) {
        val listViews = mutableListOf<CardboardViewType>()

        val header = list[0]
        val item1 = CardboardTypeCreator(header.registerUser ?: "")
        item1.isHeader = true
        listViews.add(item1)

        listViews.add(CardboardLineModel())

        for (i in list.indices) {
            val data = list[i]
            val item = CardboardTypeSpecific(
                data.senderUserName ?: "",
                data.receiverUser ?: "",
                data.wfsCrDateTime ?: "",
                data.referDescription ?: ""
            )
            if (i < list.size - 1) {
                item.isFooter = false
                listViews.add(item)
                listViews.add(CardboardLineModel())
            } else {
                item.isFooter = true
                listViews.add(item)
            }
        }

        listHistory.postValue(listViews)
    }

    fun postTransferToFolder(
        request: CardboardTransferToFolderRequest,
    ) {
        remote.postTransferToFolder(
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

    fun callControlValidation(
        request: CardboardControlValidationRequest
    ) {
        remote.callControlValidation(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardControlValidationResponse> {
                override fun onDataLoaded(data: CardboardControlValidationResponse) {
                    controlValidationResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }
}