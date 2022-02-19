package ir.nik.cardboard.data.network.model.base

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.BaseRequest
import ir.nik.cardboard.utils.PAGE_SIZE

internal open class BaseGetRequest: BaseRequest() {
    @SerializedName("jsonParameters")
    var jsonParameters: String?= null

    @SerializedName("pageNumber")
    var pageNumber: Int?= null

    @SerializedName("pageSize")
    var pageSize : Int = PAGE_SIZE

    @SerializedName("typeOperation")
    var typeOperation: Int = 0
}