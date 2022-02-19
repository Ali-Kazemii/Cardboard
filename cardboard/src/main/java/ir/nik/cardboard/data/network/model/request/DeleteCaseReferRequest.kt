package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseRequest

internal class DeleteCaseReferRequest: BaseRequest() {
    @SerializedName("wfS_CrID")
    var wfsCrId: Long?= null
}