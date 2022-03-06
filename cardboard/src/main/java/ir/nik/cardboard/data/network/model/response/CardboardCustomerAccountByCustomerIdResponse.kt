package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardCustomerAccountByCustomerIdResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("tbL_CAID")
        val caId:Long?= null

        @SerializedName("tbL_CaSubject")
        val subject: String?= null

        @SerializedName("tbL_CaTitle")
        val title: String?= null
    }
}