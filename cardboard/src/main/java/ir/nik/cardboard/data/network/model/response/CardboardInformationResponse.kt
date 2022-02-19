package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseResponse
import java.io.Serializable

internal class CardboardInformationResponse: BaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result: Serializable{
        @SerializedName("wfS_CrID")
        val wfsCrId: Long?= null

        @SerializedName("wfS_CaseID_fk")
        val wfsCaseId: Long?= null

        @SerializedName("wfS_CaseTableID")
        val wfsCaseTableId: Long?= null

        @SerializedName("wfS_PsNextID_fk")
        val wfsPsNextId: Long?= null

        @SerializedName("wfS_ProcessID_fk")
        val wfsProcessId: Long?= null

        @SerializedName("wfS_ProcessTableName")
        val wfsProcessTableName: String?= null

        @SerializedName("wfS_ProcessFarsiName")
        val wfS_ProcessFarsiName: String?= null

        @SerializedName("requestType")
        val requestType: String?= null

        @SerializedName("requestDate")
        val requestDate: String?= null

        @SerializedName("senderUser")
        val senderUser: String?= null

        @SerializedName("requestSubject")
        val requestSubject: String?= null

        @SerializedName("wfS_CrDateTime")
        val wfsCrDateTime: String?= null

        @SerializedName("wfS_CrReply")
        val wfsCrReply: String?= null

        @SerializedName("wfS_CrPreviewDateTime")
        val wfsCrPreviewDateTime: String?= null

        @SerializedName("wfS_CrActive")
        val wfsCrActive: Int?= null

        @SerializedName("idealTimeStep")
        val idealTimeStep: String?= null

        @SerializedName("actualTimeStep")
        val actualTimeStep: String?= null

        @SerializedName("nextStep")
        val nextStep: String?= null

        @SerializedName("approveStep")
        val approveStep: String?= null

        @SerializedName("abortStep")
        val abortStep: String?= null

        var isSelectable: Boolean = false

        var selected: Boolean = false
    }
}