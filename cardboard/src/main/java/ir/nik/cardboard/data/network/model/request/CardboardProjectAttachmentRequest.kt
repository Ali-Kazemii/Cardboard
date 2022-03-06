package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CardboardProjectAttachmentRequest: BaseGetRequest() {
    @SerializedName("pmS_PaID")
    var paId: Long?= null
}