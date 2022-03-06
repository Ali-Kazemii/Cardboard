package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardReceiverResponse: CardboardBaseResponse(){

    val result: MutableList<Result> ?= null

    inner class Result{
//        @SerializedName("userID")
        @SerializedName("tbL_UserID")
        val userId: Long?= null

        @SerializedName("tbL_CustomerID")
        val customerId: Long?= null

        @SerializedName("customerTitle")
        val customerTitle: String?= null

//        @SerializedName("userName")
        @SerializedName("tbL_CustomerTitle")
        val userName: String?= null

//        @SerializedName("postName")
        @SerializedName("tbL_PostTitle")
        val postName: String?= null

        @SerializedName("picture")
        val thumbnail: String?= null
    }
}