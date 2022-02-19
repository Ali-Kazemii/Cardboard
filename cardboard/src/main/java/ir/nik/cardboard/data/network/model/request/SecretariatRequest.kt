package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class SecretariatRequest: BaseGetRequest() {
    @SerializedName("oaS_SecretariatID")
    private val secretariatId: Long = 0
}