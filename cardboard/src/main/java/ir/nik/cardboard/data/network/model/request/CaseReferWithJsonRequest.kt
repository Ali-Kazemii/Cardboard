package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseRequest

internal class CaseReferWithJsonRequest: BaseRequest() {

    @SerializedName("wfS_CrIDsJson")
    var wfsCrIdJson: String?= null

    @SerializedName("TypeOperation")
    var typeOperation: Int?= null

    @SerializedName("wfS_CrRegisterDate")
    var registerDate: String= ""
}