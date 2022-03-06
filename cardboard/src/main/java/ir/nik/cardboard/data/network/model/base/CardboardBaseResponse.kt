package ir.nik.cardboard.data.network.model.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal open class CardboardBaseResponse: Serializable {
    @SerializedName("status")
    var status: Boolean?= null

    @SerializedName("message")
    var message: String?= null

    @SerializedName("statusDescription")
    var statusDescription: Int?= null

    @SerializedName("resultCount")
    val resultCount: Long?= null

    @SerializedName("dateTime")
    val dateTime: String?= null

    @SerializedName("acC_FinancialYearID")
    val financialYearId: Int?= null

    @SerializedName("messageValidation")
    val messageValidation: String?= null

    @SerializedName("tbL_UserID")
    val userId: Long?= null

    var isOfflineMode: Boolean = false
}