package ir.nik.cardboard.data.network.api

import ir.nik.cardboard.data.network.model.base.BaseGetRequest
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST

internal interface ApiInterface {


    /** #Begin Attachment ====================================================================== **/

    @HTTP(method = "DELETE", path = "DMS_DocumentAttachment/DMS_Da_Delete_MobAPI", hasBody = true)
    fun deleteFile(
        @Body request: DeleteFileRequest
    ): Call<ResponseId>

    @POST("DMS_DocumentAttachment/DMS_Da_InsertFile_MobAPI")
    fun postAttachment(
        @Body request: PostAttachmentRequest
    ): Call<ResponseId>

    @POST("DMS_DocumentAttachment/DMS_Da_GetData_MobAPI")
    fun getListAttachment(
        @Body request: DocumentAttachmentRequest
    ): Call<AttachmentListResponse>

    @POST("PMS_ProjectAttachmentType/PMS_Pat_GetData_MobAPI")
    fun getListAttachmentsType(
        @Body request: ListAttachmentTypeRequest
    ): Call<ListAttachmentTypeResponse>

    @HTTP(
        method = "DELETE",
        path = "PMS_ProjectAttachment/PMS_Pa_Delete_MobAPI",
        hasBody = true
    )
    fun deleteDocuments(
        @Body request: DeleteDocumentRequest
    ): Call<ResponseId>

    @POST("PMS_ProjectAttachment/PMS_Pa_Insert_MobAPI")
    fun insertDocumentAttachment(
        @Body request: PostDocumentAttachmentRequest
    ): Call<ResponseId>

    @POST("PMS_ProjectAttachment/PMS_Pa_Update_MobAPI")
    fun updateDocumentAttachment(
        @Body request: PostDocumentAttachmentRequest
    ): Call<ResponseId>

    @POST("CNT_ContractAttachment/CNT_Ca_GetData_MobAPI")
    fun getContractAttachment(
        @Body request: ContractAttachmentRequest
    ): Call<ContractAttachmentResponse>

    @POST("PMS_ProjectAttachment/PMS_Pa_GetData_MobAPI")
    fun getProjectAttachment(
        @Body request: ProjectAttachmentRequest
    ): Call<ProjectAttachmentResponse>

    /** #End Attachment ======================================================================== **/



    @POST("TBL_User/TBL_User_Dashboard_MobAPI")
    fun getCardboardList(
        @Body request: BaseGetRequest
    ): Call<CardboardListResponse>

    @POST("WFS_CaseReferral/WFS_Cr_GetData_MobAPI")
    fun getCardboardInformationList(
        @Body request: CardboardInformationRequest
    ): Call<CardboardInformationResponse>

    @POST("WFS_CaseReferral/WFS_Cr_SpecialOperation_MobAPI")
    fun postCaseRead(
        @Body request: CaseReadRequest
    ): Call<ResponseId>

    @POST("WFS_CaseReferral/WFS_Cr_InsertSpecific_MobAPI")
    fun postCaseReferWithJson(
        @Body request: CaseReferWithJsonRequest
    ): Call<ResponseId>


    @POST("TBL_UserFastReferral/TBL_Ufr_GetData_MobAPI")
    fun callControlValidation(
        @Body request: ControlValidationRequest
    ): Call<ControlValidationResponse>

    @POST("DMS_DocumentFolderHistory/DMS_Dfh_InsertSpecific_MobAPI")
    fun postTransferToFolder(
        @Body request: TransferToFolderRequest
    ): Call<ResponseId>


    @POST("WFS_CaseReferral/WFS_Cr_GetData_MobAPI")
    fun getCaseReferHistory(
        @Body request: CaseReferHistoryRequest
    ): Call<CaseReferHistoryResponse>


    @POST("WFS_CaseReferral/WFS_Cr_Reports_MobAPI")
    fun getCaseDetailBody(
        @Body request: CaseDetailBodyRequest
    ): Call<DocumentReportResponse>


    @POST("WFS_CaseReferral/WFS_Cr_GetData_MobAPI")
    fun getCaseDetailInformation(
        @Body request: CaseDetailInformationRequest
    ): Call<CaseDetailInformationResponse>

    @POST("TBL_UserFastReferral/TBL_Ufr_GetData_MobAPI")
    fun getFastRefers(
        @Body request: FastReferRequest
    ): Call<FastReferResponse>


    @HTTP(
        method = "DELETE",
        path = "TBL_UserFastReferral/TBL_Ufr_Delete_MobAPI",
        hasBody = true
    )
    fun deleteFastRefer(
        @Body request: DeleteFastReferRequest
    ): Call<ResponseId>


    @POST("TBL_UserFastReferral/TBL_Ufr_Insert_MobAPI")
    fun insertUserFastRefer(
        @Body request: PostFastReferRequest
    ): Call<ResponseId>


    @POST("TBL_UserFastReferral/TBL_Ufr_Update_MobAPI")
    fun updateUserFastRefer(
        @Body request: PostFastReferRequest
    ): Call<ResponseId>


    @POST("WFS_ReferenceAction/WFS_Ra_GetData_MobAPI")
    fun getReferenceActionList(
        @Body request: ReferenceActionRequest
    ): Call<ReferenceActionResponse>

    @POST("WFS_Process/WFS_Process_GetData_MobAPI")
    fun getProcessList(
        @Body request: ProcessRequest
    ): Call<ProcessResponse>

    @POST("TBL_Customer/TBL_Customer_GetData_MobAPI")
    fun getUserList(
        @Body request: UserDataRequest
    ): Call<UserListResponse>

    @POST("WFS_WfsBase/WFS_Wb_GetData_MobAPI")
    fun getReferNature(
        @Body request: BaseTypeRequest
    ): Call<BaseTypeResponse>

    @POST("WFS_ReferenceType/WFS_Rt_GetData_MobAPI")
    fun getReferenceTypeList(
        @Body request: ReferenceTypeRequest
    ): Call<ReferenceTypeResponse>

    @POST("WFS_ProcessStep/WFS_Ps_GetData_MobAPI")
    fun getProcessStepList(
        @Body request: ProcessStepRequest
    ): Call<ProcessStepResponse>

    @POST("WFS_WfsBase/WFS_Wb_GetData_MobAPI")
    fun getPriority(
        @Body request: BaseTypeRequest
    ): Call<BaseTypeResponse>

    @POST("TBL_User/TBL_User_GetData_MobAPI")
    fun getReceiverList(
        @Body request: BaseGetRequest
    ): Call<ReceiverResponse>

    @POST("OAS_Letter/OAS_Letter_SpecialOperation_MobAPI")
    fun postFinalSaveLetter(
        @Body request: PostFinalSaveLetterRequest
    ): Call<ResponseId>


    @POST("OAS_LetterExtraInfo/OAS_Lei_InsertSpecific_MobAPI")
    fun postLetterExtraInformation(
        @Body request: PostLetterExtraInformationRequest
    ): Call<ResponseId>

    @POST("BUD_Project/BUD_Project_GetData_MobAPI")
    fun getProjectSpacialList(
        @Body request: ProjectSpacialListRequest
    ): Call<ProjectSpacialListResponse>

    @POST("COM_CommercialDocument/COM_Cd_GetData_MobAPI")
    fun getCommercialDocumentByCustomerId(
        @Body request: CommercialDocumentByCustomerIdRequest
    ): Call<CommercialDocumentByCustomerIdResponse>

    @POST("TBL_CustomerAccount/TBL_Ca_GetData_MobAPI")
    fun getCustomerAccountByCustomerId(
        @Body request: CustomerAccountByCustomerIdRequest
    ): Call<CustomerAccountByCustomerIdResponse>

    @POST("CNT_Contract/CNT_Contract_GetData_MobAPI")
    fun getContractByCustomerId(
        @Body request: ContractByCustomerIdRequest
    ): Call<ContractByCustomerIdResponse>


    @POST("WFS_CaseLinked/WFS_Cl_InsertSpecific_MobAPI")
    fun postCaseLinkedListWithJson(
        @Body request: PostCaseLinkedListJsonRequest
    ): Call<ResponseId>

    @POST("WFS_WfsBase/WFS_Wb_GetData_MobAPI")
    fun getDocumentRelationTypeList(
        @Body request: DocumentRelationTypeRequest
    ): Call<DocumentRelationTypeResponse>


    @POST("OAS_Letter/OAS_Letter_GetData_MobAPI")
    fun getUserLetterList(
        @Body request: UserLetterListRequest
    ): Call<UserLetterListResponse>


    @HTTP(
        method = "DELETE",
        path = "WFS_CaseLinked/WFS_Cl_Delete_MobAPI",
        hasBody = true
    )
    fun deleteCaseLinked(
        @Body request: DeleteCaseLinkedRequest
    ): Call<ResponseId>


    @POST("WFS_CaseLinked/WFS_Cl_GetData_MobAPI")
    fun getCaseLinkedList(
        @Body request: CaseLinkedRequest
    ): Call<CaseListLinkedResponse>


    @HTTP(
        method = "DELETE",
        path = "WFS_CaseReferral/WFS_Cr_Delete_MobAPI",
        hasBody = true
    )
    fun deleteCaseRefer(
        @Body request: DeleteCaseReferRequest
    ): Call<ResponseId>


    @POST("WFS_CaseReferral/WFS_Cr_GetData_MobAPI")
    fun getCaseReferralListByWFSCaseId(
        @Body request: CaseReferralListByWFSCaseIdRequest
    ): Call<CaseReferralListByWFSCaseIdResponse>

    @POST("OAS_Letter/OAS_Letter_GetData_MobAPI")
    fun getLetterById(
        @Body request: LetterByIdRequest
    ): Call<LetterByIdResponse>

    @HTTP(
        method = "DELETE",
        path = "OAS_Letter/OAS_Letter_Delete_MobAPI",
        hasBody = true
    )
    fun deleteLetter(
        @Body request: DeleteLetterRequest
    ): Call<ResponseId>

    @POST("OAS_Letter/OAS_Letter_GetData_MobAPI")
    fun getDraftLetterList(
        @Body request: DraftLetterRequest
    ): Call<DraftLetterResponse>


    @POST("WFS_DeliveryType/WFS_Dt_GetData_MobAPI")
    fun getDocumentSendReceiveMethodList(
        @Body request: DocumentSendReceiveMethodRequest
    ): Call<DocumentSendReceiveMethodResponse>


    @POST("OAS_Letter/OAS_Letter_InsertSpecific_MobAPI")
    fun postLetter(
        @Body request: PostLetterRequest
    ): Call<ResponseId>

    @POST("TBL_Form/TBL_Form_GetData_MobAPI")
    fun getLetterType(
        @Body request: LetterTypeRequest
    ): Call<LetterTypeResponse>


    @POST("OAS_Secretariat/OAS_Secretariat_GetData_MobAPI")
    fun getSecretariatList(
        @Body request: SecretariatRequest
    ): Call<SecretariatResponse>


    @POST("TBL_AccessType/TBL_At_GetData_MobAPI")
    fun getAccessTypeList(
        @Body request: AccessTypeListRequest
    ): Call<AccessTypeListResponse>

}