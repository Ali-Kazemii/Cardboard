package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseRequest

internal class CardboardDeleteFileRequest: CardboardBaseRequest() {
    @SerializedName("dmS_DaID")
    var daId: Long?= null
}