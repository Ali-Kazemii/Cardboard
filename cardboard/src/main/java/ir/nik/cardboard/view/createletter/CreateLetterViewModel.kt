package ir.nik.cardboard.view.createletter

import androidx.lifecycle.MutableLiveData
import com.nik.cardboard.R
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration
import ir.nik.cardboard.data.network.api.CardboardRemoteRepository
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.response.*
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.view.base.CardboardBaseViewModel
import ir.nik.cardboard.view.createletter.step.StepModel

internal class CreateLetterViewModel(
    private val remote: CardboardRemoteRepository,
    pref: CardboardPreferenceConfiguration,
    calendar: PersianCalendar
) : CardboardBaseViewModel(pref, calendar, remote) {

    val secretariatResponse = MutableLiveData<CardboardSecretariatResponse>()
    val letterTypeResponse = MutableLiveData<CardboardLetterTypeResponse>()
    val documentMethodTypeResponse = MutableLiveData<CardboardDocumentSendReceiveMethodResponse>()
    val draftLetterListResponse = MutableLiveData<CardboardDraftLetterResponse>()
    val letterByIdResponse = MutableLiveData<CardboardLetterByIdResponse>()
    val listCaseReferralByWfsCaseResponse = MutableLiveData<CardboardCaseReferralListByWFSCaseIdResponse>()
    val caseLinkedResponse = MutableLiveData<CardboardCaseListLinkedResponse>()
    val userLetterListResponse = MutableLiveData<CardboardUserLetterListResponse>()
    val documentRelationTypeResponse = MutableLiveData<CardboardDocumentRelationTypeResponse>()
    val contractByCustomerIdResponse = MutableLiveData<CardboardContractByCustomerIdResponse>()
    val customerAccountByCustomerIdResponse = MutableLiveData<CardboardCustomerAccountByCustomerIdResponse>()
    val commercialDocumentByCustomerIdResponse = MutableLiveData<CardboardCommercialDocumentByCustomerIdResponse>()
    val projectSpacialResponse = MutableLiveData<CardboardProjectSpacialListResponse>()


    fun getCreateLetterSteps(): List<StepModel> {
        return listOf(
            StepModel(
                Const.LetterStep.KEY_LETTER_INFORMATION,
                "1. اطلاعات نامه",
                R.drawable.ic_letter,
            ),
            StepModel(
                Const.LetterStep.KEY_LETTER_ATTACHMENT,
                "2. ضمیمه نامه",
                R.drawable.ic_letter_attachment
            ),
            StepModel(
                Const.LetterStep.KEY_LETTER_RECEIVER,
                "3. گیرندگان",
                R.drawable.ic_letter_receiver
            ),
            StepModel(
                Const.LetterStep.KEY_LETTER_LINKED,
                "4. نامه های مرتبط",
                R.drawable.ic_letter_linked
            ),
            StepModel(
                Const.LetterStep.KEY_LETTER_EXTRA_INFORMATION,
                "5. اطلاعات تکمیلی",
                R.drawable.ic_letter_extra
            ),
            StepModel(
                Const.LetterStep.KEY_LETTER_SEND,
                "6. ثبت نهایی و ارجاع",
                R.drawable.ic_letter_register
            )
        )
    }

    fun getSecretariatList(
        request: CardboardSecretariatRequest
    ) {
        remote.getSecretariatList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardSecretariatResponse> {
                override fun onDataLoaded(data: CardboardSecretariatResponse) {
                    secretariatResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }


    fun getLetterType(
        request: CardboardLetterTypeRequest
    ) {
        remote.getLetterType(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardLetterTypeResponse> {
                override fun onDataLoaded(data: CardboardLetterTypeResponse) {
                    letterTypeResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun postLetter(request: CardboardPostLetterRequest) {
        remote.postLetter(
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

    fun getDocumentSendReceiveMethod(
        request: CardboardDocumentSendReceiveMethodRequest
    ) {
        remote.getDocumentSendReceiveMethod(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardDocumentSendReceiveMethodResponse> {
                override fun onDataLoaded(data: CardboardDocumentSendReceiveMethodResponse) {
                    documentMethodTypeResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun getDraftLetterList(
        request: CardboardDraftLetterRequest
    ) {
        remote.getDraftLetterList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardDraftLetterResponse> {
                override fun onDataLoaded(data: CardboardDraftLetterResponse) {
                    draftLetterListResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun deleteLetter(
        request: CardboardDeleteLetterRequest
    ) {
        remote.deleteLetter(
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

    fun getLetterById(
        request: CardboardLetterByIdRequest
    ) {
        remote.getLetterById(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardLetterByIdResponse> {
                override fun onDataLoaded(data: CardboardLetterByIdResponse) {
                    letterByIdResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun getCaseReferralListByWFSCaseId(
        request: CardboardCaseReferralListByWFSCaseIdRequest
    ) {
        remote.getCaseReferralListByWFSCaseId(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardCaseReferralListByWFSCaseIdResponse> {
                override fun onDataLoaded(data: CardboardCaseReferralListByWFSCaseIdResponse) {
                    listCaseReferralByWfsCaseResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun deleteCaseRefer(
        request: CardboardDeleteCaseReferRequest
    ) {
        remote.deleteCaseRefer(
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

    fun getCaseLinkedList(
        request: CardboardCaseLinkedRequest
    ) {
        remote.getCaseLinkedList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardCaseListLinkedResponse> {
                override fun onDataLoaded(data: CardboardCaseListLinkedResponse) {
                    caseLinkedResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun deleteCaseLinked(
        request: CardboardDeleteCaseLinkedRequest
    ) {
        remote.deleteCaseLinked(
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

    fun getUserLetterList(
        request: CardboardUserLetterListRequest
    ) {
        remote.getUserLetterList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardUserLetterListResponse> {
                override fun onDataLoaded(data: CardboardUserLetterListResponse) {
                    userLetterListResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun getDocumentRelationTypeList(
        request: CardboardDocumentRelationTypeRequest
    ){
        remote.getDocumentRelationTypeList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardDocumentRelationTypeResponse> {
                override fun onDataLoaded(data: CardboardDocumentRelationTypeResponse) {
                    documentRelationTypeResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun postCaseLinkedListWithJson(
        request: CardboardPostCaseLinkedListJsonRequest
    ){
        remote.postCaseLinkedListWithJson(
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

    fun getContractByCustomerId(
        request: CardboardContractByCustomerIdRequest
    ){
        remote.getContractByCustomerId(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardContractByCustomerIdResponse> {
                override fun onDataLoaded(data: CardboardContractByCustomerIdResponse) {
                    contractByCustomerIdResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun getCustomerAccountByCustomerId(
        request: CardboardCustomerAccountByCustomerIdRequest
    ){
        remote.getCustomerAccountByCustomerId(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardCustomerAccountByCustomerIdResponse> {
                override fun onDataLoaded(data: CardboardCustomerAccountByCustomerIdResponse) {
                    customerAccountByCustomerIdResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun getCommercialDocumentByCustomerId(
        request: CardboardCommercialDocumentByCustomerIdRequest
    ){
        remote.getCommercialDocumentByCustomerId(
           request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardCommercialDocumentByCustomerIdResponse> {
                override fun onDataLoaded(data: CardboardCommercialDocumentByCustomerIdResponse) {
                    commercialDocumentByCustomerIdResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun getProjectSpacialList(
        request: CardboardProjectSpacialListRequest
    ){
        remote.getProjectSpacialList(
            request ,
            object : CardboardRemoteRepository.OnApiCallback<CardboardProjectSpacialListResponse> {
                override fun onDataLoaded(data: CardboardProjectSpacialListResponse) {
                    projectSpacialResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun postLetterExtraInformation(
        request: CardboardPostLetterExtraInformationRequest
    ){
        remote.postLetterExtraInformation(
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

    fun postFinalSaveLetter(
        request: CardboardPostFinalSaveLetterRequest
    ){
        remote.postFinalSaveLetter(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardResponseId> {
                override fun onDataLoaded(data: CardboardResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                     errorPostCaseRefer.postValue(response)
                }
            })
    }


}