package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

/**
 * Created by Ali_Kazemi on 20/09/2021.
 */
internal class DocumentAttachmentRequest: BaseGetRequest() {
    @SerializedName("dmS_DaID")
    var daId: Long = 0
}