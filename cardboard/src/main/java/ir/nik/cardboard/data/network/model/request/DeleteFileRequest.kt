package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseRequest

internal class DeleteFileRequest: BaseRequest() {
    @SerializedName("dmS_DaID")
    var daId: Long?= null
}