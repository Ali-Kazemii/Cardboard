package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CardboardBaseTypeRequest: BaseGetRequest() {
    @SerializedName("wfS_WfsBaseID")
    private val baseId: Long = 0
}