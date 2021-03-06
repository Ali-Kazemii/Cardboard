package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CardboardDocumentRelationTypeRequest: BaseGetRequest() {
    @SerializedName("wfS_WfsBaseID")
    private val wfsBaseId: Long = 0
}