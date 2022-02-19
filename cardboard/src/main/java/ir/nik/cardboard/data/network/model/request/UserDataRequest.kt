package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class UserDataRequest: BaseGetRequest() {
    @SerializedName("tbL_RegistrarUserID")
    var registerUserId: Long?= null
}