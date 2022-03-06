package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseRequest

/**
 * Created by Ali_Kazemi on 28/09/2021.
 */
internal class CardboardDeleteDocumentRequest: CardboardBaseRequest() {
    @SerializedName("pmS_PaID")
    var paId: Long?= null
}