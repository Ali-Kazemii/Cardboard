package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseRequest

internal class CardboardPostLetterExtraInformationRequest: CardboardBaseRequest() {
    @SerializedName("oaS_LetterID_fk")
    var letterId: Long?= null
    
    @SerializedName("oaS_LetterPropertyJson")
    var letterPropertyJson: String?= null
    
    @SerializedName("cnT_ContractID_fk")
    var contractId: Long?= null
    
    @SerializedName("tbL_CAID_fk")
    var caId: Long?= null
    
    @SerializedName("coM_CdID_fk")
    var cdId: Long?= null
    
    @SerializedName("buD_ProjectID_fk")
    var projectId: Long?= null
    
    @SerializedName("oaS_LeiNote")
    val note: String? = null

    @SerializedName("oaS_LeiID")
    var oasLeiId: Long? = 0

    @SerializedName("wfS_AtID_fk")
    var wfsAtId: Long? = 0

    @SerializedName("wfS_DtID_fk")
    var wfsDtId: Long?= null

    @SerializedName("oaS_LeiType")
    private val type = 0

    @SerializedName("oaS_LeiActive")
    private val active = 1

    @SerializedName("oaS_LeiStatus")
    private val status = 0

    @SerializedName("oaS_LeiRegisterDate")
    var registerDate: String?= null

    @SerializedName("oaS_LeiDeleteDate")
    private val deleteDate: String = ""
}