package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseRequest

internal class CardboardLetterTypeRequest: CardboardBaseRequest() {
    @SerializedName("tbL_FormID")
    private val formId: Long = 0
}