package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardCaseReferHistoryResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: List<Result> ?= null

    inner class Result {
        @SerializedName("wfS_CrID")
        val wfsCrId: Long? = null

        @SerializedName("registerUser")
        val registerUser: String?= null

//        @SerializedName("senderUserName")
        @SerializedName("senderUser")
        val senderUserName: String? = null

        @SerializedName("reciverUser")
        val receiverUser: String? = null

        @SerializedName("wfS_CrDateTime")
        val wfsCrDateTime: String? = null

//        @SerializedName("wfS_WeReplyNote")
        @SerializedName("wfS_CrReply")
        val referDescription: String? = null

//        @SerializedName("wfS_WeStatus")
        @SerializedName("wfS_CrStatus")
        val wfsWeStatus: String? = null

//        @SerializedName("wfS_CrPursuit")
        @SerializedName("wfS_CrPursuitDateTime")
        val wfsCrPursuit: String? = null

        @SerializedName("wfS_CrAT")
        val wfsCrAt: String? = null

        @SerializedName("message")
        val historyMessage: String? = null

        /**new fields ===========================================================================**/
        @SerializedName("wfS_CrNote")
        val crNote :String?= null
    }
}