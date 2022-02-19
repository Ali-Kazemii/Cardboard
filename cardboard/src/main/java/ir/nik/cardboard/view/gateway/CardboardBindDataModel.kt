package ir.nik.cardboard.view.gateway

import java.io.Serializable

data class CardboardBindDataModel(
    val token: String,
    val hostName: String,
    val personnelId: Long,
    val postId: Long,
    val userId: Long,
    val imei: String,
    val osVersion: String,
    val deviceModel: String,
    val appVersion: String,
): Serializable
