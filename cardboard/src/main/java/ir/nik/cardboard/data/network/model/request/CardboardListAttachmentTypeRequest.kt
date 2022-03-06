package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

/**
 * Created by Ali_Kazemi on 28/09/2021.
 */
internal class CardboardListAttachmentTypeRequest: BaseGetRequest() {
    @SerializedName("pmS_PatID")
    private val patId = 0
}