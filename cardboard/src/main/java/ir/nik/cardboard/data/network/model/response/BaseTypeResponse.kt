package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse

internal class BaseTypeResponse: BaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result{
        @SerializedName("wfS_WfsBaseID")
        val baseId: Long?= null

        @SerializedName("wfS_WfsBaseName")
        val baseName: String?= null
    }
}