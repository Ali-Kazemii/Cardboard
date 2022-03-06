package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardCaseListLinkedResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: List<Result> ?= null

    inner class Result{
        @SerializedName("wfS_ClID")
        val clId: Long?= null

        @SerializedName("wfS_CaseLinkedID_fk")
        val caseLinkedId: Long?= null

        @SerializedName("oaS_LetterTitle")
        val title: String?= null
    }
}