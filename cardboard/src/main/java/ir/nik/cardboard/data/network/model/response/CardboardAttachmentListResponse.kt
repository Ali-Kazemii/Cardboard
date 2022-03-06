package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardAttachmentListResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result{
        @SerializedName("dmS_DaID")
        val daId: Long?= null

        @SerializedName("dmS_DetID_fk")
        val detId: Long?= null

        @SerializedName("dmS_DocumentID_fk")
        val documentId: Long?= null

        @SerializedName("dmS_DvID_fk")
        val dvId: Long?= null

        @SerializedName("dmS_DaCode")
        val daCode: String?= null

        @SerializedName("dmS_DaFileSize")
        val fileSize: Long?= null

        @SerializedName("dmS_DaFileName")
        val fileName: String?= null

        @SerializedName("dmS_DaDescription")
        val description: String?= null

        @SerializedName("dmS_DaNote")
        val note: String?= null

        @SerializedName("dmS_DaRegisterDate")
        val registerDate: String?= null

        @SerializedName("acC_FinancialYearID")
        val financialYearId: Long?= null

        @SerializedName("tbL_UserID")
        val userId: Long?= null

        @SerializedName("dmS_DaThumbnails")
        val thumbnail: String?= null

        @SerializedName("dmS_DetSuffix")
        val suffix: String?= null

        @SerializedName("dmS_DcID_fk")
        val dcId: Long?= null

        @SerializedName("dmS_DocumentRelatedTableId")
        val documentRelatedTableId: Long?= null

        @SerializedName("dmS_DocumentCode")
        val documentCode: String?= null

        @SerializedName("dmS_DocumentTitle")
        val documentTitle: String?= null

        @SerializedName("tbL_UserFullName")
        val userFullName: String?= null

        @SerializedName("documentID")
        val downloadDocumentId: Long?= null

        @SerializedName("documentCode")
        val downloadDocumentCode: String?= null

        @SerializedName("documentName")
        val documentName: String?= null

        @SerializedName("documentFileType")
        val documentFileType: String?= null

        @SerializedName("documentFileTypeExtention")
        val extensionType: String?= null

        @SerializedName("fileTypeId")
        val fileTypeId: Long?= null

        @SerializedName("documentRegisterDate")
        val documentRegisterDate: String?= null

        @SerializedName("documentNote")
        val documentNote: String?= null

        @SerializedName("documentBody")
        val documentBody: String?= null
    }
}