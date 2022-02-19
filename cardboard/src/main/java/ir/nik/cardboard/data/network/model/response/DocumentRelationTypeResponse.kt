package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse

internal class DocumentRelationTypeResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("wfS_WfsBaseID")
        val wfsBaseId: Long?= null

        @SerializedName("wfS_WfsBaseName")
        val wfsBaseName: String?= null
    }
}