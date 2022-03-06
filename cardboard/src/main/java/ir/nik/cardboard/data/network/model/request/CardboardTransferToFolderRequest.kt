package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseRequest

internal class CardboardTransferToFolderRequest: CardboardBaseRequest() {
    @SerializedName("dmS_DfhID")
    var dmsDfhId: Long? = 0

    @SerializedName("dmS_DocumentID_fk")
    var documentId: Long?= null

    @SerializedName("dmS_DfID_fk")
    var dfId: Long?= null

    @SerializedName("dmS_DfhNote")
    var note: String?= null

    @SerializedName("dmS_DfhRegisterDate")
    var registerDate: String?= null

    @SerializedName("dmS_DfhType")
    private val type = 0

    @SerializedName("dmS_DfhActive")
    private val active = 1

    @SerializedName("dmS_DfhStatus")
    private val status: Int = 0

    @SerializedName("dmS_DfhDeleteDate")
    private val deleteDate :String = ""
}