package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardContractAttachmentResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result>?= null

    inner class Result{
        @SerializedName("cnT_CaID")
        val caId: Long?= null

        @SerializedName("cnT_CbID")
        val cbId: Long?= null

        @SerializedName("cnT_CbName")
        val cbName: String?= null

        @SerializedName("cnT_CaDate")
        val caDate :String?= null

        @SerializedName("cnT_CaDescription")
        val caDescription: String?= null

        @SerializedName("cnT_CaNote")
        val caNote :String?= null
    }
}