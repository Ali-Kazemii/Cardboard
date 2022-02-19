package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse

internal class ReferenceTypeResponse : BaseResponse(){
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result{
        @SerializedName("textMember")
        val textMember: String?= null

        @SerializedName("valueMember")
        val valueMember: Long?= null
    }
}