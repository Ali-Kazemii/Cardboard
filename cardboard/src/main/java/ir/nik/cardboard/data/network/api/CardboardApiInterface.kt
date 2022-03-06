package ir.nik.cardboard.data.network.api

import ir.nik.cardboard.data.network.model.base.BaseGetRequest
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST

internal interface CardboardApiInterface {


    /** #Begin Attachment ====================================================================== **/

    @HTTP(method = "DELETE", path = "DMS_DocumentAttachment/DMS_Da_Delete_MobAPI", hasBody = true)
    fun deleteFile(
        @Body request: CardboardDeleteFileRequest
    ): Call<CardboardResponseId>

    @POST("DMS_DocumentAttachment/DMS_Da_InsertFile_MobAPI")
    fun postAttachment(
        @Body request: CardboardPostAttachmentRequest
    ): Call<CardboardResponseId>

    @POST("DMS_DocumentAttachment/DMS_Da_GetData_MobAPI")
    fun getListAttachment(
        @Body request: CardboardDocumentAttachmentRequest
    ): Call<CardboardAttachmentListResponse>

    @POST("PMS_ProjectAttachmentType/PMS_Pat_GetData_MobAPI")
    fun getListAttachmentsType(
        @Body request: CardboardListAttachmentTypeRequest
    ): Call<CardboardListAttachmentTypeResponse>

    @HTTP(
        method = "DELETE",
        path = "PMS_ProjectAttachment/PMS_Pa_Delete_MobAPI",
        hasBody = true
    )
    fun deleteDocuments(
        @Body request: CardboardDeleteDocumentRequest
    ): Call<CardboardResponseId>

    @POST("PMS_ProjectAttachment/PMS_Pa_Insert_MobAPI")
    fun insertDocumentAttachment(
        @Body request: CardboardPostDocumentAttachmentRequest
    ): Call<CardboardResponseId>

    @POST("PMS_ProjectAttachment/PMS_Pa_Update_MobAPI")
    fun updateDocumentAttachment(
        @Body request: CardboardPostDocumentAttachmentRequest
    ): Call<CardboardResponseId>

    @POST("CNT_ContractAttachment/CNT_Ca_GetData_MobAPI")
    fun getContractAttachment(
        @Body request: CardboardContractAttachmentRequest
    ): Call<CardboardContractAttachmentResponse>

    @POST("PMS_ProjectAttachment/PMS_Pa_GetData_MobAPI")
    fun getProjectAttachment(
        @Body request: CardboardProjectAttachmentRequest
    ): Call<CardboardProjectAttachmentResponse>

    /** #End Attachment ======================================================================== **/



    @POST("TBL_User/TBL_User_Dashboard_MobAPI")
    fun getCardboardList(
        @Body requestCardboard: BaseGetRequest
    ): Call<CardboardListResponse>

    @POST("WFS_CaseReferral/WFS_Cr_GetData_MobAPI")
    fun getCardboardInformationList(
        @Body request: CardboardInformationRequest
    ): Call<CardboardInformationResponse>

    @POST("WFS_CaseReferral/WFS_Cr_SpecialOperation_MobAPI")
    fun postCaseRead(
        @Body request: CardboardCaseReadRequest
    ): Call<CardboardResponseId>

    @POST("WFS_CaseReferral/WFS_Cr_InsertSpecific_MobAPI")
    fun postCaseReferWithJson(
        @Body request: CardboardCaseReferWithJsonRequest
    ): Call<CardboardResponseId>


    @POST("TBL_UserFastReferral/TBL_Ufr_GetData_MobAPI")
    fun callControlValidation(
        @Body request: CardboardControlValidationRequest
    ): Call<CardboardControlValidationResponse>

    @POST("DMS_DocumentFolderHistory/DMS_Dfh_InsertSpecific_MobAPI")
    fun postTransferToFolder(
        @Body request: CardboardTransferToFolderRequest
    ): Call<CardboardResponseId>


    @POST("WFS_CaseReferral/WFS_Cr_GetData_MobAPI")
    fun getCaseReferHistory(
        @Body request: CardboardCaseReferHistoryRequest
    ): Call<CardboardCaseReferHistoryResponse>


    @POST("WFS_CaseReferral/WFS_Cr_Reports_MobAPI")
    fun getCaseDetailBody(
        @Body request: CardboardCaseDetailBodyRequest
    ): Call<CardboardDocumentReportResponse>


    @POST("WFS_CaseReferral/WFS_Cr_GetData_MobAPI")
    fun getCaseDetailInformation(
        @Body request: CardboardCaseDetailInformationRequest
    ): Call<CardboardCaseDetailInformationResponse>

    @POST("TBL_UserFastReferral/TBL_Ufr_GetData_MobAPI")
    fun getFastRefers(
        @Body request: CardboardFastReferRequest
    ): Call<CardboardFastReferResponse>


    @HTTP(
        method = "DELETE",
        path = "TBL_UserFastReferral/TBL_Ufr_Delete_MobAPI",
        hasBody = true
    )
    fun deleteFastRefer(
        @Body request: CardboardDeleteFastReferRequest
    ): Call<CardboardResponseId>


    @POST("TBL_UserFastReferral/TBL_Ufr_Insert_MobAPI")
    fun insertUserFastRefer(
        @Body request: CardboardPostFastReferRequest
    ): Call<CardboardResponseId>


    @POST("TBL_UserFastReferral/TBL_Ufr_Update_MobAPI")
    fun updateUserFastRefer(
        @Body request: CardboardPostFastReferRequest
    ): Call<CardboardResponseId>


    @POST("WFS_ReferenceAction/WFS_Ra_GetData_MobAPI")
    fun getReferenceActionList(
        @Body request: CardboardReferenceActionRequest
    ): Call<CardboardReferenceActionResponse>

    @POST("WFS_Process/WFS_Process_GetData_MobAPI")
    fun getProcessList(
        @Body request: CardboardProcessRequest
    ): Call<CardboardProcessResponse>

    @POST("TBL_Customer/TBL_Customer_GetData_MobAPI")
    fun getUserList(
        @Body request: CardboardUserDataRequest
    ): Call<CardboardUserListResponse>

    @POST("WFS_WfsBase/WFS_Wb_GetData_MobAPI")
    fun getReferNature(
        @Body request: CardboardBaseTypeRequest
    ): Call<CardboardBaseTypeResponse>

    @POST("WFS_ReferenceType/WFS_Rt_GetData_MobAPI")
    fun getReferenceTypeList(
        @Body request: CardboardReferenceTypeRequest
    ): Call<CardboardReferenceTypeResponse>

    @POST("WFS_ProcessStep/WFS_Ps_GetData_MobAPI")
    fun getProcessStepList(
        @Body request: CardboardProcessStepRequest
    ): Call<CardboardProcessStepResponse>

    @POST("WFS_WfsBase/WFS_Wb_GetData_MobAPI")
    fun getPriority(
        @Body request: CardboardBaseTypeRequest
    ): Call<CardboardBaseTypeResponse>

    @POST("TBL_User/TBL_User_GetData_MobAPI")
    fun getReceiverList(
        @Body requestCardboard: BaseGetRequest
    ): Call<CardboardReceiverResponse>

    @POST("OAS_Letter/OAS_Letter_SpecialOperation_MobAPI")
    fun postFinalSaveLetter(
        @Body request: CardboardPostFinalSaveLetterRequest
    ): Call<CardboardResponseId>


    @POST("OAS_LetterExtraInfo/OAS_Lei_InsertSpecific_MobAPI")
    fun postLetterExtraInformation(
        @Body request: CardboardPostLetterExtraInformationRequest
    ): Call<CardboardResponseId>

    @POST("BUD_Project/BUD_Project_GetData_MobAPI")
    fun getProjectSpacialList(
        @Body request: CardboardProjectSpacialListRequest
    ): Call<CardboardProjectSpacialListResponse>

    @POST("COM_CommercialDocument/COM_Cd_GetData_MobAPI")
    fun getCommercialDocumentByCustomerId(
        @Body request: CardboardCommercialDocumentByCustomerIdRequest
    ): Call<CardboardCommercialDocumentByCustomerIdResponse>

    @POST("TBL_CustomerAccount/TBL_Ca_GetData_MobAPI")
    fun getCustomerAccountByCustomerId(
        @Body request: CardboardCustomerAccountByCustomerIdRequest
    ): Call<CardboardCustomerAccountByCustomerIdResponse>

    @POST("CNT_Contract/CNT_Contract_GetData_MobAPI")
    fun getContractByCustomerId(
        @Body request: CardboardContractByCustomerIdRequest
    ): Call<CardboardContractByCustomerIdResponse>


    @POST("WFS_CaseLinked/WFS_Cl_InsertSpecific_MobAPI")
    fun postCaseLinkedListWithJson(
        @Body request: CardboardPostCaseLinkedListJsonRequest
    ): Call<CardboardResponseId>

    @POST("WFS_WfsBase/WFS_Wb_GetData_MobAPI")
    fun getDocumentRelationTypeList(
        @Body request: CardboardDocumentRelationTypeRequest
    ): Call<CardboardDocumentRelationTypeResponse>


    @POST("OAS_Letter/OAS_Letter_GetData_MobAPI")
    fun getUserLetterList(
        @Body request: CardboardUserLetterListRequest
    ): Call<CardboardUserLetterListResponse>


    @HTTP(
        method = "DELETE",
        path = "WFS_CaseLinked/WFS_Cl_Delete_MobAPI",
        hasBody = true
    )
    fun deleteCaseLinked(
        @Body request: CardboardDeleteCaseLinkedRequest
    ): Call<CardboardResponseId>


    @POST("WFS_CaseLinked/WFS_Cl_GetData_MobAPI")
    fun getCaseLinkedList(
        @Body request: CardboardCaseLinkedRequest
    ): Call<CardboardCaseListLinkedResponse>


    @HTTP(
        method = "DELETE",
        path = "WFS_CaseReferral/WFS_Cr_Delete_MobAPI",
        hasBody = true
    )
    fun deleteCaseRefer(
        @Body request: CardboardDeleteCaseReferRequest
    ): Call<CardboardResponseId>


    @POST("WFS_CaseReferral/WFS_Cr_GetData_MobAPI")
    fun getCaseReferralListByWFSCaseId(
        @Body request: CardboardCaseReferralListByWFSCaseIdRequest
    ): Call<CardboardCaseReferralListByWFSCaseIdResponse>

    @POST("OAS_Letter/OAS_Letter_GetData_MobAPI")
    fun getLetterById(
        @Body request: CardboardLetterByIdRequest
    ): Call<CardboardLetterByIdResponse>

    @HTTP(
        method = "DELETE",
        path = "OAS_Letter/OAS_Letter_Delete_MobAPI",
        hasBody = true
    )
    fun deleteLetter(
        @Body request: CardboardDeleteLetterRequest
    ): Call<CardboardResponseId>

    @POST("OAS_Letter/OAS_Letter_GetData_MobAPI")
    fun getDraftLetterList(
        @Body request: CardboardDraftLetterRequest
    ): Call<CardboardDraftLetterResponse>


    @POST("WFS_DeliveryType/WFS_Dt_GetData_MobAPI")
    fun getDocumentSendReceiveMethodList(
        @Body request: CardboardDocumentSendReceiveMethodRequest
    ): Call<CardboardDocumentSendReceiveMethodResponse>


    @POST("OAS_Letter/OAS_Letter_InsertSpecific_MobAPI")
    fun postLetter(
        @Body request: CardboardPostLetterRequest
    ): Call<CardboardResponseId>

    @POST("TBL_Form/TBL_Form_GetData_MobAPI")
    fun getLetterType(
        @Body request: CardboardLetterTypeRequest
    ): Call<CardboardLetterTypeResponse>


    @POST("OAS_Secretariat/OAS_Secretariat_GetData_MobAPI")
    fun getSecretariatList(
        @Body request: CardboardSecretariatRequest
    ): Call<CardboardSecretariatResponse>


    @POST("TBL_AccessType/TBL_At_GetData_MobAPI")
    fun getAccessTypeList(
        @Body request: CardboardAccessTypeListRequest
    ): Call<CardboardAccessTypeListResponse>

}