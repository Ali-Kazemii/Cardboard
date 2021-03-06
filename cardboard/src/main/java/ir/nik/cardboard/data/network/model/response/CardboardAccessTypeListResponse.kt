package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardAccessTypeListResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result{
        @SerializedName("tbL_AtID")
        val atId: Long?= null

        @SerializedName("tbL_AtName")
        val atName: String?= null
    }
}