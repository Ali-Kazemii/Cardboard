package ir.nik.cardboard.data.local

import android.content.Context
import android.content.SharedPreferences
import ir.nik.cardboard.utils.Const.KEY_ACCESS_TOKEN
import ir.nik.cardboard.utils.Const.KEY_APP_VERSION
import ir.nik.cardboard.utils.Const.KEY_DEVICE_MODEL
import ir.nik.cardboard.utils.Const.KEY_DOCUMENT_END_DATE
import ir.nik.cardboard.utils.Const.KEY_DOCUMENT_START_DATE
import ir.nik.cardboard.utils.Const.KEY_END_DATE
import ir.nik.cardboard.utils.Const.KEY_HOST_NAME
import ir.nik.cardboard.utils.Const.KEY_IMEI
import ir.nik.cardboard.utils.Const.KEY_LOG_OUT
import ir.nik.cardboard.utils.Const.KEY_OS_VERSION
import ir.nik.cardboard.utils.Const.KEY_PERSONNEL_ID
import ir.nik.cardboard.utils.Const.KEY_POST_ID
import ir.nik.cardboard.utils.Const.KEY_PREFERENCE_NAME
import ir.nik.cardboard.utils.Const.KEY_START_DATE
import ir.nik.cardboard.utils.Const.KEY_USER_ID

internal class PreferenceConfiguration(
    context: Context
) {

    private var pref: SharedPreferences =
        context.getSharedPreferences(KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)

    var isLogout: Boolean
        get() = pref.getBoolean(KEY_LOG_OUT, false)
        set(value) {
            pref.edit().putBoolean(KEY_LOG_OUT, value).apply()
        }

    var personnelId: Long
        get() = pref.getLong(KEY_PERSONNEL_ID, 0)
        set(value) {
            pref.edit().putLong(KEY_PERSONNEL_ID, value).apply()
        }

    var accessToken: String
        get() = pref.getString(KEY_ACCESS_TOKEN, "")!!
        set(value) = pref.edit().putString(KEY_ACCESS_TOKEN, value).apply()

    var hostName: String
        get() = pref.getString(KEY_HOST_NAME, "")!!
        set(value) = pref.edit().putString(KEY_HOST_NAME, value).apply()


    var appVersion: String
        get() = pref.getString(KEY_APP_VERSION, "")!!
        set(value) {
            pref.edit().putString(KEY_APP_VERSION, value).apply()
        }

    var imei: String
        get() = pref.getString(KEY_IMEI, "")!!
        set(value) {
            pref.edit().putString(KEY_IMEI, value).apply()
        }

    var osVersion: String
        get() = pref.getString(KEY_OS_VERSION, "")!!
        set(value) {
            pref.edit().putString(KEY_OS_VERSION, value).apply()
        }

    var deviceModel: String
        get() = pref.getString(KEY_DEVICE_MODEL, "")!!
        set(value) {
            pref.edit().putString(KEY_DEVICE_MODEL, value).apply()
        }

    var postId: Long
        get() = pref.getLong(KEY_POST_ID, 0)
        set(value) {
            pref.edit().putLong(KEY_POST_ID, value).apply()
        }

    var userId: Long
        get() = pref.getLong(KEY_USER_ID, 0)
        set(value) {
            pref.edit().putLong(KEY_USER_ID, value).apply()
        }

    var documentStatDate: String
        get() = pref.getString(KEY_DOCUMENT_START_DATE, "")!!
        set(value) {
            pref.edit().putString(KEY_DOCUMENT_START_DATE, value).apply()
        }

    var documentEndDate: String
        get() = pref.getString(KEY_DOCUMENT_END_DATE, "")!!
        set(value) {
            pref.edit().putString(KEY_DOCUMENT_END_DATE, value).apply()
        }

    var startDate: String
        get() = pref.getString(KEY_START_DATE, " ")!!
        set(value) {
            pref.edit().putString(KEY_START_DATE, value).apply()
        }

    var endDate: String
        get() = pref.getString(KEY_END_DATE, " ")!!
        set(value) {
            pref.edit().putString(KEY_END_DATE, value).apply()
        }
}