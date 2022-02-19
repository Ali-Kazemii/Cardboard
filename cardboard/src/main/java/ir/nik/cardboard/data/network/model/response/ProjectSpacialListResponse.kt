package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse

internal class ProjectSpacialListResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result> ?= null

    inner class Result{
        @SerializedName("buD_ProjectID")
        val projectId: Long?= null

        @SerializedName("buD_ProjectParentID_fk")
        val projectParentId: Long?= null

        @SerializedName("buD_ProjectName")
        val projectName: String?= null
    }
}