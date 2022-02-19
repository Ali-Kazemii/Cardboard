package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse

internal class FastReferResponse: BaseResponse(){
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result{
        @SerializedName("tbL_UfrID")
        val ufrId: Long?= 0

        @SerializedName("tbL_UserID_fk")
        val userId: Long?= null

        @SerializedName("tbL_UserIDReceiver_fk")
        val userIdReceiver: Long?= null

        @SerializedName("tbL_UserReceiverFullName")
        val userReceiverFullName: String?=  null

        @SerializedName("tbL_PostIDReceiver_fk")
        val postIdReceiver: Long?= null

        @SerializedName("wfS_RtID_fk")
        val wfsRtId: Long?= null

        @SerializedName("wfS_RtName")
        val wfsRtName: String?= null

        @SerializedName("wfS_Dt_fk")
        val wfsDtId: Long?= null

        @SerializedName("wfS_DtName")
        val wfsDtName: String?= null

        @SerializedName("wfS_NrID_fk")
        val wfsNrId: Long?= null

        @SerializedName("wfS_NrName")
        val wfsNrName: String?= null

        @SerializedName("wfS_CrReply")
        val description: String?= null

        @SerializedName("tbL_UfrTitle")
        val title: String?= null
    }
}