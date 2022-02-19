package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse

internal class ProcessStepResponse: BaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result{
//        @SerializedName("wfS_PsID")
        @SerializedName("valueMember")
        val wfsPsId: Long?= null

//        @SerializedName("wfS_PsName")
        @SerializedName("textMember")
        val wfsPsName: String?= null
    }
}