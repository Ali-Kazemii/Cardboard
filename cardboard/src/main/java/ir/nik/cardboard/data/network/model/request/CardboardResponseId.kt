package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardResponseId: CardboardBaseResponse() {
    @SerializedName("result")
    val result: Long?= null
}