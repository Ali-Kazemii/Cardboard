package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseRequest

/**
 * Created by Ali_Kazemi on 28/09/2021.
 */
internal class DeleteDocumentRequest: BaseRequest() {
    @SerializedName("pmS_PaID")
    var paId: Long?= null
}