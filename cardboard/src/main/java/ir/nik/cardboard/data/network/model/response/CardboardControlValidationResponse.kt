package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardControlValidationResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("messageValidation")
        val messageValidation: String?= null
    }
}