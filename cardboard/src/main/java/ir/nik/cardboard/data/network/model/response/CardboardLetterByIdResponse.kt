package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardLetterByIdResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: List<Result> ?= null

    inner class Result{
        @SerializedName("oaS_LetterID")
        val letterId: Long?= null

        @SerializedName("oaS_SecretariatID_fk")
        val secretariatId: Long?= null

        @SerializedName("oaS_SecretariatName")
        val secretariatName: String?= null

        @SerializedName("tbL_FormID_fk")
        val formId: Int?= null

        @SerializedName("tbL_FormFarsiName")
        val formName: String?= null

        @SerializedName("tbL_CustomerID_fk")
        val customerId: Long?= null

        @SerializedName("tbL_CustomerTitle")
        val customerTitle: String?= null

        @SerializedName("oaS_LetterOtherCustomer")
        val otherCustomer: String?= null

        @SerializedName("oaS_LetterTextBody")
        val letterBody: String?= null

        @SerializedName("oaS_LetterSubject")
        val letterSubject: String?= null

        @SerializedName("oaS_InLetterNo")
        val inLetterNo: String?= null

        @SerializedName("oaS_InLetterDate")
        val inLetterDate: String?= null

        @SerializedName("wfS_AtID_fk")
        val atId: Long?= null

        @SerializedName("wfS_AtName")
        val atName: String?= null

        @SerializedName("wfS_CasePriorityID_fk")
        val casePriorityId: Long?= null

        @SerializedName("wfS_CasePriorityName")
        val casePriorityName: String?= null

        @SerializedName("oaS_LetterRegisterDate")
        val letterRegisterDate: String?= null

        @SerializedName("wfS_DtID_fk")
        val dtId: Long?= null

        @SerializedName("wfS_DtName")
        val dtName: String?= null
    }
}