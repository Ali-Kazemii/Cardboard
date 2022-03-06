package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CardboardDocumentSendReceiveMethodRequest: BaseGetRequest() {
    @SerializedName("wfS_DtID")
    private val wfsDtId: Long = 0
}