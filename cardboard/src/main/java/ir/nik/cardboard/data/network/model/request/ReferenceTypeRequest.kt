package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class ReferenceTypeRequest: BaseGetRequest() {
    @SerializedName("wfS_RtID")
    private val rtId: Long = 0
}