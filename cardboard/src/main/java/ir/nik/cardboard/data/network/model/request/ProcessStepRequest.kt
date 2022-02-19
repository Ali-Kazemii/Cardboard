package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseGetRequest

internal class ProcessStepRequest: BaseGetRequest() {
    @SerializedName("wfS_PsID")
    private val psId: Long?= null

}