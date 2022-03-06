package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseRequest

internal class CardboardPostAttachmentRequest : CardboardBaseRequest() {

    @SerializedName("dmS_DocumentID_fk")
    private val documentId: Long = 0

    @SerializedName("dmS_DaType")
    private val type: Int = 0

    @SerializedName("dmS_DaActive")
    private val active: Int = 1

    @SerializedName("dmS_DaStatus")
    private val status: Int = 0

    @SerializedName("dmS_DaDeleteDate")
    private val deleteDate: String? = null

    @SerializedName("dmS_DocumentCode")
    private val documentCode: String? = null

    @SerializedName("dmS_DaID")
    private val daId: Long = 0

    @SerializedName("dmS_DaCode")
    private val daCode: String? = null

    @SerializedName("dmS_DaDescription")
    private val description: String? = null

    @SerializedName("dmS_DaNote")
    private val note: String? = null

    //-----------------------------------------------------

    @SerializedName("dmS_DocumentRelatedTableID")
    var documentRelatedTableId: Long? = null

    @SerializedName("dmS_DocumentRelatedTableName")
    var documentRelatedTableName: String? = null

    @SerializedName("dmS_DcID")
    var dcId: Long? = null

    @SerializedName("dmS_DetID_fk")
    var detId: Long? = null

    @SerializedName("dmS_DvID_fk")
    var dvId: Long? = null

    @SerializedName("dmS_DatID_fk")
    var datId: Long? = null

    @SerializedName("dmS_DaFileSize")
    var fileSize: Long? = null

    @SerializedName("dmS_DaFileName")
    var fileName: String? = null

    @SerializedName("dmS_DaRegisterDate")
    var registerDate: String? = null

    @SerializedName("dmS_DaBody")
    var body: String? = null

}