package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseRequest

internal class DeleteFastReferRequest: BaseRequest() {
    @SerializedName("tbL_UfrID")
    var ufrId: Long?= null
}