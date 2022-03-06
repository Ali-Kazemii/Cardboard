package ir.nik.cardboard.data.network.model.utt

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal class CardboardUttWfsModel: Serializable {

    @SerializedName("RowNum")
    var rowNum: Int = 0

    @SerializedName("WFS_CrID")
    var wfsCrId: Long = 0

    @SerializedName("WFS_CaseID")
    var wfsCaseId: Long = 0

    @SerializedName("WFS_ProcessID_fk")
    var wfsProcessId: Long = 0

    @SerializedName("TBL_UserIDSender_fk")
    var userIdSender: Long = 0

    @SerializedName("TBL_UserIDReceiver_fk")
    var userIdReceiver: Long = 0

    @SerializedName("TBL_PostIDSender_fk")
    var postIdSender: Long = 0

    @SerializedName("TBL_PostIDReceiver_fk")
    var postIdReceiver: Long = 0

    @SerializedName("WFS_PsPreviousID_fk")
    var wfsPreviousId: Long = 0

    @SerializedName("WFS_PsNextID_fk")
    var wfsNextId: Long = 0

    @SerializedName("WFS_CrPriorityID_fk")
    var wfsCrPriorityId: Long = 0

    @SerializedName("WFS_RtID_fk")
    var wfsRtId: Long = 0

    @SerializedName("WFS_Dt_fk")
    var wfsDt: Long = 0

    @SerializedName("WFS_CrReply")
    var wfsCrReply: String = ""

    @SerializedName("WFS_CrNote")
    var wfsCrNote: String = ""

    @SerializedName("WFS_CrPursuitDateTime")
    var wfsCrPursuitDateTime: String = ""

    @SerializedName("WFS_CrType")
    var wfsCrType: Int = 0

    @SerializedName("WFS_CrStatus")
    var wfsCrStatus: Int = 0

    @SerializedName("IsValid")
    var isValid: Int = 0

    @SerializedName("WFS_CrSubject")
    var wfsCrSubject: String = ""

    @SerializedName("WFS_NrID_fk")
    var wfsNrId: Long = 0

    @SerializedName("WFS_RaID_fk") //سرویس های جدید معادل 14000829 سطر 98
    var wfsRaId: Long?= 0
}