package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardBaseTypeResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result{
        @SerializedName("wfS_WfsBaseID")
        val baseId: Long?= null

        @SerializedName("wfS_WfsBaseName")
        val baseName: String?= null
    }
}