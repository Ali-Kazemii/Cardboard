package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CardboardReferenceActionRequest: BaseGetRequest() {
    @SerializedName("wfS_RaID")
    private val raId: Long = 0
}