package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardSecretariatResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result{

        @SerializedName("valueMember")
        val secretariatId: Long?= null

        @SerializedName("textMember")
        val name: String?= null
    }
}