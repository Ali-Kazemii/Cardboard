package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseRequest

internal class CardboardPostDocumentAttachmentRequest : CardboardBaseRequest() {

    @SerializedName("pmS_PaID")
    var paId: Long? = null

    @SerializedName("buD_ProjectID_fk")
    var projectId: Long? = null

    @SerializedName("pmS_WbsprID_fk")
    var wbsPrId: Long? = null

    @SerializedName("pmS_PatID_fk")
    var patId: Long? = null

    @SerializedName("pmS_PaDate")
    var date: String? = null

    @SerializedName("pmS_PaDescription")
    var description: String? = null

    @SerializedName("pmS_PaRegisterDate")
    var registerDate: String? = null



    @SerializedName("pmS_PaNote")
    private val paNote: String? = null

    @SerializedName("pmS_PaActive")
    private val paActive: Int = 1

    @SerializedName("pmS_PaStaus")
    private val paStatus: Int = 0

    @SerializedName("pmS_PaType")
    private val paType: Int = 0

    @SerializedName("pmS_PaDeleteDate")
    private val deleteDate: String? = null
}