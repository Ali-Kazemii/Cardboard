package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse

internal class CommercialDocumentByCustomerIdResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("valueMember")
        val cdId: Long?= null

        @SerializedName("textMember")
        val cdTitle: String?= null
    }
}