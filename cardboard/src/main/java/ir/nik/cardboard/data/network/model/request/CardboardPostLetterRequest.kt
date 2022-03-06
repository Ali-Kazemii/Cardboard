package ir.nik.cardboard.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nik.cardboard.data.network.model.base.CardboardBaseRequest

internal class CardboardPostLetterRequest : CardboardBaseRequest() {
    @SerializedName("oaS_LetterID")
    var letterId: Long? = null

    @SerializedName("oaS_LetterParentID_fk")
    var letterParentId: Long = 0   //static

    @SerializedName("oaS_SecretariatID_fk")
    var secretariatId: Long? = null

    @SerializedName("tbL_FormID_fk")
    var formId: Int? = null

    @SerializedName("wfS_AtID_fk")
    var atId: Long? = null

    @SerializedName("wfS_DtID_fk")
    var documentMethodType: Long?= null

    @SerializedName("wfS_CasePriorityID_fk")
    var priorityId: Long? = null

    @SerializedName("tbL_LetterCreatorUserID_fk")
    var creatorUserId: Long? = null

    @SerializedName("tbL_LetterCreatorPostID_fk")
    var creatorPostId: Long? = null

    @SerializedName("tbL_CustomerID_fk")
    var customerId: Long?= 0

    @SerializedName("oaS_LetterOtherCustomer")
    var otherCustomer: String? = null

    @SerializedName("oaS_LetterTextBody")
    var letterTextBody: String? = null

    @SerializedName("oaS_LetterDate")
    var letterDate: String? = null

    @SerializedName("oaS_LetterSubject")
    var letterSubject: String? = null //static

    @SerializedName("oaS_LetterVerifyDatetime")
    var verifyDateTime: String? = null //static

    @SerializedName("oaS_InLetterNo")
    var inLetterNumber: String? = null

    @SerializedName("oaS_InLetterDate")
    var inLetterDate: String? = null

    @SerializedName("oaS_LetterNote")
    var note: String? = null

    @SerializedName("oaS_LetterNumber")
    private val letterNumber: String = ""

    @SerializedName("oaS_LetterType")
    private val type = 0

    @SerializedName("oaS_LetterActive")
    private val active = 1

    @SerializedName("oaS_LetterStatus")
    private val status = 0

    @SerializedName("oaS_LetterRegisterDate")
    var registerDate: String?= null

    @SerializedName("oaS_LetterDeleteDate")
    private val deleteDate: String = ""

}