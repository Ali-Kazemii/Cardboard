package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseRequest

internal class PostFastReferRequest: BaseRequest() {
    @SerializedName("tbL_UfrID")
    var ufrId: Long?= null

    @SerializedName("tbL_UserID_fk")
    var userId_fk: Long?= null

    @SerializedName("tbL_UserIDReceiver_fk")
    var userIdReceiver: Long?= null

    @SerializedName("tbL_PostIDReceiver_fk")
    var postIdReceiver: Long?= null

    @SerializedName("wfS_RtID_fk")
    var wfsRtId: Long?= null

    @SerializedName("wfS_Dt_fk")
    var wfsDtId: Long?= null

    @SerializedName("wfS_NrID_fk")
    var wfsNrId: Long?= null

    @SerializedName("wfS_CrReply")
    var wfsCrReply: String?= null

    @SerializedName("tbL_UfrTitle")
    var ufrTitle: String?= null

    @SerializedName("tbL_UfrNote")
    private val ufrNote: String?= null

    @SerializedName("tbL_UfrRegisterDate")
    var registerDate: String?= null

    @SerializedName("tbL_UfrType")
    private val type: Int = 0

    @SerializedName("tbL_UfrActive")
    private val active = 1

    @SerializedName("tbL_UfrStatus")
    private val status: Int = 1

    @SerializedName("tbL_UfrDeleteDate")
    private val deleteDate :String = ""
}