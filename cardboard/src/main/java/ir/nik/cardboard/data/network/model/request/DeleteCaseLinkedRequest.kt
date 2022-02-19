package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseRequest

internal class DeleteCaseLinkedRequest: BaseRequest() {
    @SerializedName("wfS_ClID")
    var clId: Long?= null
}