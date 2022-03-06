package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseRequest

internal class CardboardDeleteCaseLinkedRequest: CardboardBaseRequest() {
    @SerializedName("wfS_ClID")
    var clId: Long?= null
}