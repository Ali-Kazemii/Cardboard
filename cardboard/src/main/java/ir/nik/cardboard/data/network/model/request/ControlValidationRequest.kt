package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class ControlValidationRequest: BaseGetRequest() {

    @SerializedName("tbL_UfrID")
    private val ufrId: Long = 0
}