package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardCaseReferralListByWFSCaseIdResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("wfS_CrID")
        val wfsCrId: Long ?= null

        @SerializedName("reciverCustomer")
        val receiverCustomer: Long?= null

        @SerializedName("receiverPostID")
        val receiverPostId: Long?= null

        @SerializedName("reciverUser")
        val receiverUser: String?= null

        @SerializedName("wfS_RtID_fk")
        val rtId: Long?= null

        @SerializedName("wfS_RtName")
        val rtName: String?= null

        @SerializedName("wfS_Dt_fk")
        val dtId: Long?= null

        @SerializedName("wfS_DtName")
        val dtName: String?= null

        @SerializedName("wfS_CrPriorityID_fk")
        val priorityId: Long?= null

        @SerializedName("wfS_CrPriorityName")
        val priorityName: String?= null

        @SerializedName("wfS_CrReply")
        val crReply: String?= null

        @SerializedName("wfS_CrType")
        val crType: Int?= null

        @SerializedName("wfS_CrTypeName")
        val crTypeName: String?= null
    }
}