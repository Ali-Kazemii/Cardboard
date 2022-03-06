package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CardboardProcessRequest: BaseGetRequest() {
    @SerializedName("wfS_ProcessID")
    private val processId: Long = 0
}