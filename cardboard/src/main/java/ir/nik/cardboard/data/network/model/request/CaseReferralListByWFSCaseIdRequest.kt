package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CaseReferralListByWFSCaseIdRequest: BaseGetRequest() {
    @SerializedName("wfS_CrID")
    private val crId: Long = 0
}