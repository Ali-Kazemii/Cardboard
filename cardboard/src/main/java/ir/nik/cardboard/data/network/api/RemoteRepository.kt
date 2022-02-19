package ir.nik.cardboard.data.network.api

import android.content.Context
import android.content.Intent
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.yToast
import ir.nik.cardboard.data.local.PreferenceConfiguration
import ir.nik.cardboard.data.network.model.base.BaseGetRequest
import ir.nik.cardboard.data.network.model.base.BaseResponse
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.response.*
import ir.nik.cardboard.utils.ErrorKey
import ir.nik.cardboard.view.gateway.GatewayActivity
import okhttp3.Headers

internal class RemoteRepository(
    private val context: Context,
    private val pref: PreferenceConfiguration,
    private val api: ApiInterface
) {

    companion object{
        const val ERROR_AUTHORIZATION = "زمان استفاده از برنامه به پایان رسیده... مجددا لاگین کنید"
    }

    val onLogout: ( ()-> Unit )?= null

    val onDownloadVersion: ( ()-> Unit )?= null


    interface OnApiCallback<Model> {
        fun onDataLoaded(data: Model)
        fun onError(response: BaseResponse?)
    }

    private fun handleError(body: BaseResponse) {
        when (body.statusDescription) {
            ErrorKey.AUTHORIZATION -> context.showLogin()

            ErrorKey.DOWNLOAD_VERSION -> onDownloadVersion?.invoke()
        }
    }



    private fun Context.showLogin() {
        yToast(
           ERROR_AUTHORIZATION,
            MessageStatus.ERROR
        )
        pref.isLogout = true
        val intent = Intent(this, GatewayActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


    /** #Begin Cardboard ========================================================================**/
    fun getCardboardList(
        request: BaseGetRequest,
        callback: OnApiCallback<CardboardListResponse>
    ) {
        val call = api.getCardboardList(request)
        call.enqueue(object : ApiCallback<CardboardListResponse>() {
            override fun response(response: CardboardListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getCardboardInformationList(
        request: CardboardInformationRequest,
        callback: OnApiCallback<CardboardInformationResponse>
    ) {
        val call = api.getCardboardInformationList(request)
        call.enqueue(object : ApiCallback<CardboardInformationResponse>() {
            override fun response(response: CardboardInformationResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun postCaseRead(
        request: CaseReadRequest,
        callback: OnApiCallback<BaseResponse>
    ) {
        val call = api.postCaseRead(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun postCaseReferWithJson(
        request: CaseReferWithJsonRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.postCaseReferWithJson(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun callControlValidation(
        request: ControlValidationRequest,
        callback: OnApiCallback<ControlValidationResponse>
    ) {
        val call = api.callControlValidation(request)
        call.enqueue(object : ApiCallback<ControlValidationResponse>() {
            override fun response(response: ControlValidationResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun postTransferToFolder(
        request: TransferToFolderRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.postTransferToFolder(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }



    fun getCaseReferHistory(
        request: CaseReferHistoryRequest,
        callback: OnApiCallback<CaseReferHistoryResponse>
    ) {
        val call = api.getCaseReferHistory(request)
        call.enqueue(object : ApiCallback<CaseReferHistoryResponse>() {
            override fun response(response: CaseReferHistoryResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getCaseDetailBody(
        request: CaseDetailBodyRequest,
        callback: OnApiCallback<DocumentReportResponse>
    ) {
        val call = api.getCaseDetailBody(request)
        call.enqueue(object : ApiCallback<DocumentReportResponse>() {
            override fun response(response: DocumentReportResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getCaseDetailInformation(
        request: CaseDetailInformationRequest,
        callback: OnApiCallback<CaseDetailInformationResponse>
    ) {
        val call = api.getCaseDetailInformation(request)
        call.enqueue(object : ApiCallback<CaseDetailInformationResponse>() {
            override fun response(response: CaseDetailInformationResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun updateUserFastRefer(
        request: PostFastReferRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.updateUserFastRefer(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun insertUserFastRefer(
        request: PostFastReferRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.insertUserFastRefer(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun deleteFastRefer(
        request: DeleteFastReferRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.deleteFastRefer(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getFastRefers(
        request: FastReferRequest,
        callback: OnApiCallback<FastReferResponse>
    ) {
        val call = api.getFastRefers(request)
        call.enqueue(object : ApiCallback<FastReferResponse>() {
            override fun response(response: FastReferResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getReferenceActionList(
        request: ReferenceActionRequest,
        callback: OnApiCallback<ReferenceActionResponse>
    ) {
        val call = api.getReferenceActionList(request)
        call.enqueue(object : ApiCallback<ReferenceActionResponse>() {
            override fun response(response: ReferenceActionResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getProcessList(
        request: ProcessRequest,
        callback: OnApiCallback<ProcessResponse>
    ) {
        val call = api.getProcessList(request)
        call.enqueue(object : ApiCallback<ProcessResponse>() {
            override fun response(response: ProcessResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getUserList(
        request: UserDataRequest,
        callback: OnApiCallback<UserListResponse>
    ) {
        val call = api.getUserList(request)
        call.enqueue(object : ApiCallback<UserListResponse>() {
            override fun response(response: UserListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getReferNature(
        request: BaseTypeRequest,
        callback: OnApiCallback<BaseTypeResponse>
    ) {
        val call = api.getReferNature(request)
        call.enqueue(object : ApiCallback<BaseTypeResponse>() {
            override fun response(response: BaseTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getReferenceTypeList(
        request: ReferenceTypeRequest,
        callback: OnApiCallback<ReferenceTypeResponse>
    ) {
        val call = api.getReferenceTypeList(request)
        call.enqueue(object : ApiCallback<ReferenceTypeResponse>() {
            override fun response(response: ReferenceTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getProcessStepList(
        request: ProcessStepRequest,
        callback: OnApiCallback<ProcessStepResponse>
    ) {
        val call = api.getProcessStepList(request)
        call.enqueue(object : ApiCallback<ProcessStepResponse>() {
            override fun response(response: ProcessStepResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getPriority(
        request: BaseTypeRequest,
        callback: OnApiCallback<BaseTypeResponse>
    ) {
        val call = api.getPriority(request)
        call.enqueue(object : ApiCallback<BaseTypeResponse>() {
            override fun response(response: BaseTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getReceiverList(
        request: BaseGetRequest,
        callback: OnApiCallback<ReceiverResponse>
    ) {
        val call = api.getReceiverList(request)
        call.enqueue(object : ApiCallback<ReceiverResponse>() {
            override fun response(response: ReceiverResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun postFinalSaveLetter(
        request: PostFinalSaveLetterRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.postFinalSaveLetter(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun postLetterExtraInformation(
        request: PostLetterExtraInformationRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.postLetterExtraInformation(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getProjectSpacialList(
        request: ProjectSpacialListRequest,
        callback: OnApiCallback<ProjectSpacialListResponse>
    ) {
        val call = api.getProjectSpacialList(request )
        call.enqueue(object : ApiCallback<ProjectSpacialListResponse>() {
            override fun response(response: ProjectSpacialListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getCommercialDocumentByCustomerId(
        request: CommercialDocumentByCustomerIdRequest,
        callback: OnApiCallback<CommercialDocumentByCustomerIdResponse>
    ) {
        val call = api.getCommercialDocumentByCustomerId(request)
        call.enqueue(object : ApiCallback<CommercialDocumentByCustomerIdResponse>() {
            override fun response(
                response: CommercialDocumentByCustomerIdResponse,
                headers: Headers
            ) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getCustomerAccountByCustomerId(
        request: CustomerAccountByCustomerIdRequest,
        callback: OnApiCallback<CustomerAccountByCustomerIdResponse>
    ) {
        val call = api.getCustomerAccountByCustomerId(request)
        call.enqueue(object : ApiCallback<CustomerAccountByCustomerIdResponse>() {
            override fun response(response: CustomerAccountByCustomerIdResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getContractByCustomerId(
        request: ContractByCustomerIdRequest,
        callback: OnApiCallback<ContractByCustomerIdResponse>
    ) {
        val call = api.getContractByCustomerId(request)
        call.enqueue(object : ApiCallback<ContractByCustomerIdResponse>() {
            override fun response(response: ContractByCustomerIdResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun postCaseLinkedListWithJson(
        request: PostCaseLinkedListJsonRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.postCaseLinkedListWithJson(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getDocumentRelationTypeList(
        request: DocumentRelationTypeRequest,
        callback: OnApiCallback<DocumentRelationTypeResponse>
    ) {
        val call = api.getDocumentRelationTypeList(request)
        call.enqueue(object : ApiCallback<DocumentRelationTypeResponse>() {
            override fun response(response: DocumentRelationTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getUserLetterList(
        request: UserLetterListRequest,
        callback: OnApiCallback<UserLetterListResponse>
    ) {
        val call = api.getUserLetterList(request)
        call.enqueue(object : ApiCallback<UserLetterListResponse>() {
            override fun response(response: UserLetterListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun deleteCaseLinked(
        request: DeleteCaseLinkedRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.deleteCaseLinked(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getCaseLinkedList(
        request: CaseLinkedRequest,
        callback: OnApiCallback<CaseListLinkedResponse>
    ) {
        val call = api.getCaseLinkedList(request)
        call.enqueue(object : ApiCallback<CaseListLinkedResponse>() {
            override fun response(response: CaseListLinkedResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun deleteCaseRefer(
        request: DeleteCaseReferRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.deleteCaseRefer(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getCaseReferralListByWFSCaseId(
        request: CaseReferralListByWFSCaseIdRequest,
        callback: OnApiCallback<CaseReferralListByWFSCaseIdResponse>
    ) {
        val call = api.getCaseReferralListByWFSCaseId(request)
        call.enqueue(object : ApiCallback<CaseReferralListByWFSCaseIdResponse>() {
            override fun response(response: CaseReferralListByWFSCaseIdResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getLetterById(
        request: LetterByIdRequest,
        callback: OnApiCallback<LetterByIdResponse>
    ) {
        val call = api.getLetterById(request)
        call.enqueue(object : ApiCallback<LetterByIdResponse>() {
            override fun response(response: LetterByIdResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun deleteLetter(
        request: DeleteLetterRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.deleteLetter(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }



    fun getDraftLetterList(
        request: DraftLetterRequest,
        callback: OnApiCallback<DraftLetterResponse>
    ) {
        val call = api.getDraftLetterList(request)
        call.enqueue(object : ApiCallback<DraftLetterResponse>() {
            override fun response(response: DraftLetterResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getDocumentSendReceiveMethod(
        request: DocumentSendReceiveMethodRequest,
        callback: OnApiCallback<DocumentSendReceiveMethodResponse>
    ) {
        val call = api.getDocumentSendReceiveMethodList(request)
        call.enqueue(object : ApiCallback<DocumentSendReceiveMethodResponse>() {
            override fun response(response: DocumentSendReceiveMethodResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun postLetter(
        request: PostLetterRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.postLetter(request)
        call.enqueue(object : ApiCallback<ResponseId>() {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getLetterType(
        request: LetterTypeRequest,
        callback: OnApiCallback<LetterTypeResponse>
    ) {
        val call = api.getLetterType(request)
        call.enqueue(object : ApiCallback<LetterTypeResponse>() {
            override fun response(response: LetterTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getSecretariatList(
        request: SecretariatRequest,
        callback: OnApiCallback<SecretariatResponse>
    ) {
        val call = api.getSecretariatList(request)
        call.enqueue(object : ApiCallback<SecretariatResponse>() {
            override fun response(response: SecretariatResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getAccessTypeList(
        request: AccessTypeListRequest,
        callback: OnApiCallback<AccessTypeListResponse>
    ) {
        val call = api.getAccessTypeList(request)
        call.enqueue(object : ApiCallback<AccessTypeListResponse>() {
            override fun response(response: AccessTypeListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    /** #End Cardboard ==========================================================================**/

    /** #Begin Attachment =======================================================================**/
    fun insertProjectAttachments(
        request: PostDocumentAttachmentRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.insertDocumentAttachment(request)
        call.enqueue(object : ApiCallback<ResponseId>(){
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun updateProjectAttachments(
        request: PostDocumentAttachmentRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.updateDocumentAttachment(request)
        call.enqueue(object : ApiCallback<ResponseId>(){
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getListAttachment(
        request: DocumentAttachmentRequest,
        callback: OnApiCallback<AttachmentListResponse>
    ) {
        val call = api.getListAttachment(request)
        call.enqueue(object : ApiCallback<AttachmentListResponse>(){
            override fun response(response: AttachmentListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getListAttachmentsType(
        request: ListAttachmentTypeRequest,
        callback: OnApiCallback<ListAttachmentTypeResponse>
    ) {
        val call = api.getListAttachmentsType(request)
        call.enqueue(object : ApiCallback<ListAttachmentTypeResponse>(){
            override fun response(response: ListAttachmentTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun postAttachment(
        request: PostAttachmentRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.postAttachment(request)
        call.enqueue(object : ApiCallback<ResponseId>(){
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun deleteFile(
        request: DeleteFileRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.deleteFile(request)
        call.enqueue(object : ApiCallback<ResponseId>(){
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    /** #End Attachment =========================================================================**/

}