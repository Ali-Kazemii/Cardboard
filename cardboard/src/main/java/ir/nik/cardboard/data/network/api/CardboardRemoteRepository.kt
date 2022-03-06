package ir.nik.cardboard.data.network.api

import android.content.Context
import android.content.Intent
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.yToast
import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration
import ir.nik.cardboard.data.network.model.base.BaseGetRequest
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.response.*
import ir.nik.cardboard.utils.ErrorKey
import ir.nik.cardboard.view.gateway.GatewayActivity
import okhttp3.Headers

internal class CardboardRemoteRepository(
    private val context: Context,
    private val pref: CardboardPreferenceConfiguration,
    private val api: CardboardApiInterface
) {

    companion object{
        const val ERROR_AUTHORIZATION = "زمان استفاده از برنامه به پایان رسیده... مجددا لاگین کنید"
    }

    val onLogout: ( ()-> Unit )?= null

    val onDownloadVersion: ( ()-> Unit )?= null


    interface OnApiCallback<Model> {
        fun onDataLoaded(data: Model)
        fun onError(response: CardboardBaseResponse?)
    }

    private fun handleError(body: CardboardBaseResponse) {
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
        call.enqueue(object : CardboardApiCallback<CardboardListResponse>() {
            override fun response(response: CardboardListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        call.enqueue(object : CardboardApiCallback<CardboardInformationResponse>() {
            override fun response(response: CardboardInformationResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardCaseReadRequest,
        callback: OnApiCallback<CardboardBaseResponse>
    ) {
        val call = api.postCaseRead(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardCaseReferWithJsonRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.postCaseReferWithJson(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardControlValidationRequest,
        callback: OnApiCallback<CardboardControlValidationResponse>
    ) {
        val call = api.callControlValidation(request)
        call.enqueue(object : CardboardApiCallback<CardboardControlValidationResponse>() {
            override fun response(response: CardboardControlValidationResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardTransferToFolderRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.postTransferToFolder(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardCaseReferHistoryRequest,
        callback: OnApiCallback<CardboardCaseReferHistoryResponse>
    ) {
        val call = api.getCaseReferHistory(request)
        call.enqueue(object : CardboardApiCallback<CardboardCaseReferHistoryResponse>() {
            override fun response(response: CardboardCaseReferHistoryResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardCaseDetailBodyRequest,
        callback: OnApiCallback<CardboardDocumentReportResponse>
    ) {
        val call = api.getCaseDetailBody(request)
        call.enqueue(object : CardboardApiCallback<CardboardDocumentReportResponse>() {
            override fun response(response: CardboardDocumentReportResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardCaseDetailInformationRequest,
        callback: OnApiCallback<CardboardCaseDetailInformationResponse>
    ) {
        val call = api.getCaseDetailInformation(request)
        call.enqueue(object : CardboardApiCallback<CardboardCaseDetailInformationResponse>() {
            override fun response(response: CardboardCaseDetailInformationResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardPostFastReferRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.updateUserFastRefer(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardPostFastReferRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.insertUserFastRefer(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardDeleteFastReferRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.deleteFastRefer(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardFastReferRequest,
        callback: OnApiCallback<CardboardFastReferResponse>
    ) {
        val call = api.getFastRefers(request)
        call.enqueue(object : CardboardApiCallback<CardboardFastReferResponse>() {
            override fun response(response: CardboardFastReferResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardReferenceActionRequest,
        callback: OnApiCallback<CardboardReferenceActionResponse>
    ) {
        val call = api.getReferenceActionList(request)
        call.enqueue(object : CardboardApiCallback<CardboardReferenceActionResponse>() {
            override fun response(response: CardboardReferenceActionResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardProcessRequest,
        callback: OnApiCallback<CardboardProcessResponse>
    ) {
        val call = api.getProcessList(request)
        call.enqueue(object : CardboardApiCallback<CardboardProcessResponse>() {
            override fun response(response: CardboardProcessResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardUserDataRequest,
        callback: OnApiCallback<CardboardUserListResponse>
    ) {
        val call = api.getUserList(request)
        call.enqueue(object : CardboardApiCallback<CardboardUserListResponse>() {
            override fun response(response: CardboardUserListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardBaseTypeRequest,
        callback: OnApiCallback<CardboardBaseTypeResponse>
    ) {
        val call = api.getReferNature(request)
        call.enqueue(object : CardboardApiCallback<CardboardBaseTypeResponse>() {
            override fun response(response: CardboardBaseTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardReferenceTypeRequest,
        callback: OnApiCallback<CardboardReferenceTypeResponse>
    ) {
        val call = api.getReferenceTypeList(request)
        call.enqueue(object : CardboardApiCallback<CardboardReferenceTypeResponse>() {
            override fun response(response: CardboardReferenceTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardProcessStepRequest,
        callback: OnApiCallback<CardboardProcessStepResponse>
    ) {
        val call = api.getProcessStepList(request)
        call.enqueue(object : CardboardApiCallback<CardboardProcessStepResponse>() {
            override fun response(response: CardboardProcessStepResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardBaseTypeRequest,
        callback: OnApiCallback<CardboardBaseTypeResponse>
    ) {
        val call = api.getPriority(request)
        call.enqueue(object : CardboardApiCallback<CardboardBaseTypeResponse>() {
            override fun response(response: CardboardBaseTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        callback: OnApiCallback<CardboardReceiverResponse>
    ) {
        val call = api.getReceiverList(request)
        call.enqueue(object : CardboardApiCallback<CardboardReceiverResponse>() {
            override fun response(response: CardboardReceiverResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardPostFinalSaveLetterRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.postFinalSaveLetter(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardPostLetterExtraInformationRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.postLetterExtraInformation(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardProjectSpacialListRequest,
        callback: OnApiCallback<CardboardProjectSpacialListResponse>
    ) {
        val call = api.getProjectSpacialList(request )
        call.enqueue(object : CardboardApiCallback<CardboardProjectSpacialListResponse>() {
            override fun response(response: CardboardProjectSpacialListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardCommercialDocumentByCustomerIdRequest,
        callback: OnApiCallback<CardboardCommercialDocumentByCustomerIdResponse>
    ) {
        val call = api.getCommercialDocumentByCustomerId(request)
        call.enqueue(object : CardboardApiCallback<CardboardCommercialDocumentByCustomerIdResponse>() {
            override fun response(
                response: CardboardCommercialDocumentByCustomerIdResponse,
                headers: Headers
            ) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardCustomerAccountByCustomerIdRequest,
        callback: OnApiCallback<CardboardCustomerAccountByCustomerIdResponse>
    ) {
        val call = api.getCustomerAccountByCustomerId(request)
        call.enqueue(object : CardboardApiCallback<CardboardCustomerAccountByCustomerIdResponse>() {
            override fun response(response: CardboardCustomerAccountByCustomerIdResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardContractByCustomerIdRequest,
        callback: OnApiCallback<CardboardContractByCustomerIdResponse>
    ) {
        val call = api.getContractByCustomerId(request)
        call.enqueue(object : CardboardApiCallback<CardboardContractByCustomerIdResponse>() {
            override fun response(response: CardboardContractByCustomerIdResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardPostCaseLinkedListJsonRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.postCaseLinkedListWithJson(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardDocumentRelationTypeRequest,
        callback: OnApiCallback<CardboardDocumentRelationTypeResponse>
    ) {
        val call = api.getDocumentRelationTypeList(request)
        call.enqueue(object : CardboardApiCallback<CardboardDocumentRelationTypeResponse>() {
            override fun response(response: CardboardDocumentRelationTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardUserLetterListRequest,
        callback: OnApiCallback<CardboardUserLetterListResponse>
    ) {
        val call = api.getUserLetterList(request)
        call.enqueue(object : CardboardApiCallback<CardboardUserLetterListResponse>() {
            override fun response(response: CardboardUserLetterListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardDeleteCaseLinkedRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.deleteCaseLinked(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardCaseLinkedRequest,
        callback: OnApiCallback<CardboardCaseListLinkedResponse>
    ) {
        val call = api.getCaseLinkedList(request)
        call.enqueue(object : CardboardApiCallback<CardboardCaseListLinkedResponse>() {
            override fun response(response: CardboardCaseListLinkedResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardDeleteCaseReferRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.deleteCaseRefer(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardCaseReferralListByWFSCaseIdRequest,
        callback: OnApiCallback<CardboardCaseReferralListByWFSCaseIdResponse>
    ) {
        val call = api.getCaseReferralListByWFSCaseId(request)
        call.enqueue(object : CardboardApiCallback<CardboardCaseReferralListByWFSCaseIdResponse>() {
            override fun response(response: CardboardCaseReferralListByWFSCaseIdResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardLetterByIdRequest,
        callback: OnApiCallback<CardboardLetterByIdResponse>
    ) {
        val call = api.getLetterById(request)
        call.enqueue(object : CardboardApiCallback<CardboardLetterByIdResponse>() {
            override fun response(response: CardboardLetterByIdResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardDeleteLetterRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.deleteLetter(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardDraftLetterRequest,
        callback: OnApiCallback<CardboardDraftLetterResponse>
    ) {
        val call = api.getDraftLetterList(request)
        call.enqueue(object : CardboardApiCallback<CardboardDraftLetterResponse>() {
            override fun response(response: CardboardDraftLetterResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardDocumentSendReceiveMethodRequest,
        callback: OnApiCallback<CardboardDocumentSendReceiveMethodResponse>
    ) {
        val call = api.getDocumentSendReceiveMethodList(request)
        call.enqueue(object : CardboardApiCallback<CardboardDocumentSendReceiveMethodResponse>() {
            override fun response(response: CardboardDocumentSendReceiveMethodResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardPostLetterRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.postLetter(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>() {
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardLetterTypeRequest,
        callback: OnApiCallback<CardboardLetterTypeResponse>
    ) {
        val call = api.getLetterType(request)
        call.enqueue(object : CardboardApiCallback<CardboardLetterTypeResponse>() {
            override fun response(response: CardboardLetterTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardSecretariatRequest,
        callback: OnApiCallback<CardboardSecretariatResponse>
    ) {
        val call = api.getSecretariatList(request)
        call.enqueue(object : CardboardApiCallback<CardboardSecretariatResponse>() {
            override fun response(response: CardboardSecretariatResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardAccessTypeListRequest,
        callback: OnApiCallback<CardboardAccessTypeListResponse>
    ) {
        val call = api.getAccessTypeList(request)
        call.enqueue(object : CardboardApiCallback<CardboardAccessTypeListResponse>() {
            override fun response(response: CardboardAccessTypeListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardPostDocumentAttachmentRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.insertDocumentAttachment(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>(){
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardPostDocumentAttachmentRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.updateDocumentAttachment(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>(){
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardDocumentAttachmentRequest,
        callback: OnApiCallback<CardboardAttachmentListResponse>
    ) {
        val call = api.getListAttachment(request)
        call.enqueue(object : CardboardApiCallback<CardboardAttachmentListResponse>(){
            override fun response(response: CardboardAttachmentListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardListAttachmentTypeRequest,
        callback: OnApiCallback<CardboardListAttachmentTypeResponse>
    ) {
        val call = api.getListAttachmentsType(request)
        call.enqueue(object : CardboardApiCallback<CardboardListAttachmentTypeResponse>(){
            override fun response(response: CardboardListAttachmentTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardPostAttachmentRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.postAttachment(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>(){
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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
        request: CardboardDeleteFileRequest,
        callback: OnApiCallback<CardboardResponseId>
    ) {
        val call = api.deleteFile(request)
        call.enqueue(object : CardboardApiCallback<CardboardResponseId>(){
            override fun response(response: CardboardResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: CardboardBaseResponse?) {
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