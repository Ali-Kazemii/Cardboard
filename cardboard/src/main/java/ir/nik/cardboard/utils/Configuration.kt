package ir.nik.cardboard.utils

import android.os.Environment
import java.io.File

const val SSID_AUTOMATION = 982
const val SSID_SUPERVISORS = 986
const val SSID_MANAGERS = 981
const val SSID_CONTRACTORS = 983
const val SSID_WAREHOUSE = 989
const val SSID_ASSET_MANAGEMENT = 917
const val SSID_VALUE_ENGINEERING = 906


const val PAGE_SIZE = 50

const val SSID: Int = SSID_MANAGERS

const val APP_NAME_FARSI:String = "پورتال مدیران"

val PATH_STORAGE = Environment.getExternalStorageDirectory().toString() + File.separator + APP_NAME_FARSI

val isSecure: Boolean
    get() = false

