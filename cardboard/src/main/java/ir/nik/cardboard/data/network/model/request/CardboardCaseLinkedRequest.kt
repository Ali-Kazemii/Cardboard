package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CardboardCaseLinkedRequest: BaseGetRequest() {
    @SerializedName("wfS_ClID")
    private val wfsClId: Long = 0
}