package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardDocumentReportResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: String? = null
}