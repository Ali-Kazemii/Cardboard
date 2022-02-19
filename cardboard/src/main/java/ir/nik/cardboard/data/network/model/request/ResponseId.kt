package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse

internal class ResponseId: BaseResponse() {
    @SerializedName("result")
    val result: Long?= null
}