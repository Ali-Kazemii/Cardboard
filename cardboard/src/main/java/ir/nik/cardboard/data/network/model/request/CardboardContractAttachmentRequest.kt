package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CardboardContractAttachmentRequest : BaseGetRequest(){
    @SerializedName("cnT_CaID")
    var caId: Long?= null
}