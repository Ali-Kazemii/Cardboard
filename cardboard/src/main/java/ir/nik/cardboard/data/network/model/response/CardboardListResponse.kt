package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse

internal class CardboardListResponse: BaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result{
        @SerializedName("id")
        val id: Long?= null

        @SerializedName("description")
        val name: String?= null

        @SerializedName("iconBody")
        val iconUrl: String?= null

        @SerializedName("total")
        val totalCount: Long?= null
    }
}