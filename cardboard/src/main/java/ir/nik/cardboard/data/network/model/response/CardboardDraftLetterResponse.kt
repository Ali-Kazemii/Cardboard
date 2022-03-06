package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardDraftLetterResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: List<Result> ?= null

    inner class Result{
        @SerializedName("oaS_LetterID")
        val letterId: Long?= null

        @SerializedName("wfS_CaseID")
        val wfsCaseId: Long?= null

        @SerializedName("tbL_FormID_fk")
        val formId: Int?= null

        @SerializedName("tbL_CustomerID_fk")
        val customerId: Long?= null

        @SerializedName("oaS_LetterTitle")
        val letterTitle: String?= null

        @SerializedName("oaS_LetterInfoStatus")
        val letterInformationStatus: Int?= null

        @SerializedName("oaS_LetterInfoDescription")
        val letterInfoDescription: String?= null

        @SerializedName("oaS_LetterReferralStatus")
        val letterReferralStatus: Int?= null

        @SerializedName("oaS_LetterReferralDescription")
        val letterReferralDescription: String?= null

        @SerializedName("oaS_LetterLinkStatus")
        val letterLinkStatus: Int?= null

        @SerializedName("oaS_LetterLinkDescription")
        val letterLinkDescription: String?= null

        @SerializedName("oaS_LetterExtraInfoStatus")
        val letterExtraInformationStatus: Int?= null

        @SerializedName("oaS_LetterExtraInfoDescription")
        val letterExtraInformationDescription: String?= null

        @SerializedName("oaS_LetterAttachmentStatus")
        val letterAttachmentStatus: Int?= null

        @SerializedName("oaS_LetterAttachmentDescription")
        val letterAttachmentDescription: String?= null
    }
}