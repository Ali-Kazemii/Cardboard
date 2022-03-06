package ir.nik.cardboard.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse

internal class CardboardUserListResponse: CardboardBaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result {
        @SerializedName("tbL_CustomerID")
        val customerId: Long? = null

        @SerializedName("tbL_CustomerTitle")
        val customerTitle: String? = null

        @SerializedName("tbL_UserID")
        val userId: Long? = null

        @SerializedName("tbL_UserName")
        val userName: String? = null

        @SerializedName("tbL_PostID_fk")
        val postId: Long? = null

        @SerializedName("tbL_PostTitle")
        val postTitle: String? = null

        @SerializedName("tbL_UserFullName")
        val userFullName: String? = null

        @SerializedName("tbL_UtName")
        val utName: String? = null

        @SerializedName("tbL_UserMobileNo")
        val mobile: String? = null

        @SerializedName("tbL_UserEmail")
        val email: String? = null

        @SerializedName("tbL_UserNationalNo")
        val nationalNo: String? = null

        @SerializedName("tbL_PalceName_Default")
        val placeName: String? = null

        @SerializedName("tbL_CustomerFullTitle")
        val customerFullTitle: String? = null
    }
}