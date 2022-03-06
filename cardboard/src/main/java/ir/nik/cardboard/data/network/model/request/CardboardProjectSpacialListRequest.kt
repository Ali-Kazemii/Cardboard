package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CardboardProjectSpacialListRequest: BaseGetRequest() {
    @SerializedName("buD_ProjectID")
    private val projectId: Long = 0
}