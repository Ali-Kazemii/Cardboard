package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CardboardCustomerAccountByCustomerIdRequest: BaseGetRequest() {
    @SerializedName("tbL_CaID")
    private val caId: Long = 0
}