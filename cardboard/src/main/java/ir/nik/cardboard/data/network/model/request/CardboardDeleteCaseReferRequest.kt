package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseRequest

internal class CardboardDeleteCaseReferRequest: CardboardBaseRequest() {
    @SerializedName("wfS_CrID")
    var wfsCrId: Long?= null
}