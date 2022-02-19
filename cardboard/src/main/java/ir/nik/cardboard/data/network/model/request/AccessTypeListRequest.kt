package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class AccessTypeListRequest: BaseGetRequest() {
    @SerializedName("tbL_AtID")
    private val atId: Long?= null
}