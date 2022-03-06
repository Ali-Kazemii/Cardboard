package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CardboardPostFinalSaveLetterRequest: BaseGetRequest() {
    @SerializedName("oaS_LetterID")
    var oasLetterId: Long?= null
}