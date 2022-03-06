package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardProjectAttachmentResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result>?= null

    inner class Result{
        @SerializedName("pmS_PaID")
        val paId: Long?= null

        @SerializedName("pmS_PatID_fk")
        val patId: Long?= null

        @SerializedName("pmS_PatName")
        val patName: String?= null

        @SerializedName("pmS_PaDate")
        val paDate: String?= null

        @SerializedName("pmS_PaDescription")
        val description: String?= null

        @SerializedName("pmS_PaNote")
        val note: String?= null

        @SerializedName("tbL_CustomerTitle")
        val customerTitle: String?= null
    }
}