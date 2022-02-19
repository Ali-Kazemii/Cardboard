package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse

internal class DocumentSendReceiveMethodResponse: BaseResponse() {
    @SerializedName("result")
    val result :List<Result> ?= null

    inner class Result{
        @SerializedName("valueMember")
        val wfsDtId: Long?= null

        @SerializedName("textMember")
        val wfsDtName:String?= null
    }
}