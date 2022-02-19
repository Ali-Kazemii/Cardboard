package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class DraftLetterRequest: BaseGetRequest() {
    @SerializedName("oaS_LetterID")
    private val oasLetterId: Long= 0
}