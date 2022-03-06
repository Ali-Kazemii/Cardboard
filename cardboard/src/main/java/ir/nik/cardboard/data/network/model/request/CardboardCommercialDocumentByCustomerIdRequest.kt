package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class CardboardCommercialDocumentByCustomerIdRequest: BaseGetRequest() {
    @SerializedName("coM_CdID")
    private val comCdId: Long = 0
}