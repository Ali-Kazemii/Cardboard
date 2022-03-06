package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseRequest

internal class CardboardPostCaseLinkedListJsonRequest: CardboardBaseRequest() {
    @SerializedName("wfS_CaseID_fk")
    var wfsCaseId: Long?= null

    @SerializedName("wfS_ClJson")
    var json: String?= null

    @SerializedName("wfS_ClNote")
    var note: String?= null

    @SerializedName("wfS_ClType")
    private val type = 0

    @SerializedName("wfS_ClActive")
    private val active = 1

    @SerializedName("wfS_ClStatus")
    private val status = 0

    @SerializedName("wfS_ClRegisterDate")
    var registerDate: String?= null

    @SerializedName("wfS_ClDeleteDate")
    private val deleteDate: String = ""
}