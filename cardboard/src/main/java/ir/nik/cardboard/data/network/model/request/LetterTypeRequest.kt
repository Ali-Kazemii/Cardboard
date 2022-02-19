package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseRequest

internal class LetterTypeRequest: BaseRequest() {
    @SerializedName("tbL_FormID")
    private val formId: Long = 0
}