package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class ContractByCustomerIdRequest: BaseGetRequest() {
    @SerializedName("cnT_ContractID")
    private val contractId: Long = 0
}