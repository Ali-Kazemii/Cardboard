package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse

internal class LetterTypeResponse: BaseResponse(){
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("tbL_FormID")
        val formId: Long?= null

        @SerializedName("tbL_FormFarsiName")
        val name: String?= null

        @SerializedName("tbL_FormParentID_fk")
        val formParentId: Long?= null

        @SerializedName("tbL_FormDescription")
        val description: String?= null
    }
}