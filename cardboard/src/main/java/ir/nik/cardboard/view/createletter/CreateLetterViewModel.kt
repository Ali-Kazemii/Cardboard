package ir.nik.cardboard.view.createletter

import androidx.lifecycle.MutableLiveData
import com.nik.cardboard.R
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.PreferenceConfiguration
import ir.nik.cardboard.data.network.api.RemoteRepository
import ir.nik.cardboard.data.network.model.base.BaseResponse
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.response.*
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.view.base.BaseViewModel
import ir.nik.cardboard.view.createletter.step.StepModel

internal class CreateLetterViewModel(
    private val remote: RemoteRepository,
    pref: PreferenceConfiguration,
    calendar: PersianCalendar
) : BaseViewModel(pref, calendar, remote) {

    val secretariatResponse = MutableLiveData<SecretariatResponse>()
    val letterTypeResponse = MutableLiveData<LetterTypeResponse>()
    val documentMethodTypeResponse = MutableLiveData<DocumentSendReceiveMethodResponse>()
    val draftLetterListResponse = MutableLiveData<DraftLetterResponse>()
    val letterByIdResponse = MutableLiveData<LetterByIdResponse>()
    val listCaseReferralByWfsCaseResponse = MutableLiveData<CaseReferralListByWFSCaseIdResponse>()
    val caseLinkedResponse = MutableLiveData<CaseListLinkedResponse>()
    val userLetterListResponse = MutableLiveData<UserLetterListResponse>()
    val documentRelationTypeResponse = MutableLiveData<DocumentRelationTypeResponse>()
    val contractByCustomerIdResponse = MutableLiveData<ContractByCustomerIdResponse>()
    val customerAccountByCustomerIdResponse = MutableLiveData<CustomerAccountByCustomerIdResponse>()
    val commercialDocumentByCustomerIdResponse = MutableLiveData<CommercialDocumentByCustomerIdResponse>()
    val projectSpacialResponse = MutableLiveData<ProjectSpacialListResponse>()


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
        request: SecretariatRequest
    ) {
        remote.getSecretariatList(
            request,
            object : RemoteRepository.OnApiCallback<SecretariatResponse> {
                override fun onDataLoaded(data: SecretariatResponse) {
                    secretariatResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }


    fun getLetterType(
        request: LetterTypeRequest
    ) {
        remote.getLetterType(
            request,
            object : RemoteRepository.OnApiCallback<LetterTypeResponse> {
                override fun onDataLoaded(data: LetterTypeResponse) {
                    letterTypeResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun postLetter(request: PostLetterRequest) {
        remote.postLetter(
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

    fun getDocumentSendReceiveMethod(
        request: DocumentSendReceiveMethodRequest
    ) {
        remote.getDocumentSendReceiveMethod(
            request,
            object : RemoteRepository.OnApiCallback<DocumentSendReceiveMethodResponse> {
                override fun onDataLoaded(data: DocumentSendReceiveMethodResponse) {
                    documentMethodTypeResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun getDraftLetterList(
        request: DraftLetterRequest
    ) {
        remote.getDraftLetterList(
            request,
            object : RemoteRepository.OnApiCallback<DraftLetterResponse> {
                override fun onDataLoaded(data: DraftLetterResponse) {
                    draftLetterListResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun deleteLetter(
        request: DeleteLetterRequest
    ) {
        remote.deleteLetter(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     errorDelete.postValue(response)
                }
            })
    }

    fun getLetterById(
        request: LetterByIdRequest
    ) {
        remote.getLetterById(
            request,
            object : RemoteRepository.OnApiCallback<LetterByIdResponse> {
                override fun onDataLoaded(data: LetterByIdResponse) {
                    letterByIdResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun getCaseReferralListByWFSCaseId(
        request: CaseReferralListByWFSCaseIdRequest
    ) {
        remote.getCaseReferralListByWFSCaseId(
            request,
            object : RemoteRepository.OnApiCallback<CaseReferralListByWFSCaseIdResponse> {
                override fun onDataLoaded(data: CaseReferralListByWFSCaseIdResponse) {
                    listCaseReferralByWfsCaseResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun deleteCaseRefer(
        request: DeleteCaseReferRequest
    ) {
        remote.deleteCaseRefer(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     errorDelete.postValue(response)
                }
            })
    }

    fun getCaseLinkedList(
        request: CaseLinkedRequest
    ) {
        remote.getCaseLinkedList(
            request,
            object : RemoteRepository.OnApiCallback<CaseListLinkedResponse> {
                override fun onDataLoaded(data: CaseListLinkedResponse) {
                    caseLinkedResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun deleteCaseLinked(
        request: DeleteCaseLinkedRequest
    ) {
        remote.deleteCaseLinked(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     errorDelete.postValue(response)
                }
            })
    }

    fun getUserLetterList(
        request: UserLetterListRequest
    ) {
        remote.getUserLetterList(
            request,
            object : RemoteRepository.OnApiCallback<UserLetterListResponse> {
                override fun onDataLoaded(data: UserLetterListResponse) {
                    userLetterListResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun getDocumentRelationTypeList(
        request: DocumentRelationTypeRequest
    ){
        remote.getDocumentRelationTypeList(
            request,
            object : RemoteRepository.OnApiCallback<DocumentRelationTypeResponse> {
                override fun onDataLoaded(data: DocumentRelationTypeResponse) {
                    documentRelationTypeResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun postCaseLinkedListWithJson(
        request: PostCaseLinkedListJsonRequest
    ){
        remote.postCaseLinkedListWithJson(
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

    fun getContractByCustomerId(
        request: ContractByCustomerIdRequest
    ){
        remote.getContractByCustomerId(
            request,
            object : RemoteRepository.OnApiCallback<ContractByCustomerIdResponse> {
                override fun onDataLoaded(data: ContractByCustomerIdResponse) {
                    contractByCustomerIdResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun getCustomerAccountByCustomerId(
        request: CustomerAccountByCustomerIdRequest
    ){
        remote.getCustomerAccountByCustomerId(
            request,
            object : RemoteRepository.OnApiCallback<CustomerAccountByCustomerIdResponse> {
                override fun onDataLoaded(data: CustomerAccountByCustomerIdResponse) {
                    customerAccountByCustomerIdResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun getCommercialDocumentByCustomerId(
        request: CommercialDocumentByCustomerIdRequest
    ){
        remote.getCommercialDocumentByCustomerId(
           request,
            object : RemoteRepository.OnApiCallback<CommercialDocumentByCustomerIdResponse> {
                override fun onDataLoaded(data: CommercialDocumentByCustomerIdResponse) {
                    commercialDocumentByCustomerIdResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun getProjectSpacialList(
        request: ProjectSpacialListRequest
    ){
        remote.getProjectSpacialList(
            request ,
            object : RemoteRepository.OnApiCallback<ProjectSpacialListResponse> {
                override fun onDataLoaded(data: ProjectSpacialListResponse) {
                    projectSpacialResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     error.postValue(response)
                }
            })
    }

    fun postLetterExtraInformation(
        request: PostLetterExtraInformationRequest
    ){
        remote.postLetterExtraInformation(
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

    fun postFinalSaveLetter(
        request: PostFinalSaveLetterRequest
    ){
        remote.postFinalSaveLetter(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                     errorPostCaseRefer.postValue(response)
                }
            })
    }


}