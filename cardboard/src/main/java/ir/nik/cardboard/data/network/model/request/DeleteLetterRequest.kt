package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseRequest

internal class DeleteLetterRequest: BaseRequest() {
    @SerializedName("oaS_LetterID")
    var letterId: Long?= null
}