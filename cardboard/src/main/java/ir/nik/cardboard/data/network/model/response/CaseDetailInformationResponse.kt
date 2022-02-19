package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse

internal class CaseDetailInformationResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("textMember")
        val textMember: String?= null
    }
}