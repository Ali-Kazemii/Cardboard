package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CaseDetailInformationRequest: BaseGetRequest() {
    @SerializedName("wfS_CrID")
    var wfsCrId: Long? = 0
}