package ir.nik.cardboard.utils

internal object Const {

    const val APP_NAME = "Cardboard"
    const val KEY_PREFERENCE_NAME = "cardboard"

    const val KEY_LOG_OUT= "logout"

    const val KEY_CARDBOARD = "card_board"
    const val KEY_CASE_LIST = "case_list"

    const val KEY_IMEI = "imei"
    const val KEY_OS_VERSION = "os_version"
    const val KEY_DEVICE_MODEL = "device_model"
    const val KEY_APP_VERSION = "app_version"
    const val KEY_HOST_NAME = "host_name"
    const val KEY_PERSONNEL_ID= "personnelId"
    const val KEY_ACCESS_TOKEN= "access_token"

    const val KEY_POST_ID= "postId"
    const val KEY_USER_ID= "user_id"
    const val KEY_DOCUMENT_START_DATE = "document_start_date"
    const val KEY_DOCUMENT_END_DATE = "document_end_date"
    const val KEY_START_DATE= "start_date"
    const val KEY_END_DATE= "end_date"

    const val KEY_CARTABLE_INFO= "card_board_info"
    const val KEY_REFER = "refer"
    const val KEY_CASE_LIST_TYPE = "case_list_type"


    const val KEY_ATTACHMENT_TYPE = "attachment_type"
    const val KEY_DC_ID: String = "dc_id"
    const val KEY_RELATED_TABLE_ID = "related_table_id"
    const val KEY_RELATED_TABLE_NAME = "related_table_name"


    object AccountingType{
        const val DEMAND = "AR_FIS_DetailDemands.mrt"
        const val DEBT = "AR_FIS_DetailDebts.mrt"
    }

    object ReferOtherOperation{
        const val KEY_NEXT_STEP = 1
        const val KEY_APPROVE_END = 3
        const val KEY_DISAPPROVE_END = 4
    }

    object StatusReference{
        const val KEY_ALL = 0
        const val KEY_REFERRED = 1
        const val KEY_NOT_REFERENCED = 2
    }

    object BaseType{
        const val STEP_STATUS = 1        //وضعیت مرحله
        const val SPECIAL_USER = 2       //کاربران ویژه
        const val PRIORITY = 3           //درجه اهمیت
        const val DOCUMENT_RELATIVE = 6  //انواع ارتباط مدارک
        const val REFER_NATURE = 19      //ماهیت ارجاع

    }

    object LetterStep{
        const val KEY_LETTER_INFORMATION = "letter_information"
        const val KEY_LETTER_ATTACHMENT = "letter_attachment"
        const val KEY_LETTER_RECEIVER = "letter_receiver"
        const val KEY_LETTER_LINKED = "letter_linked"
        const val KEY_LETTER_EXTRA_INFORMATION = "letter_extra_information"
        const val KEY_LETTER_SEND = "letter_send"
    }

    object LetterTableName{
        const val LETTER = "OAS_Letter"
    }

    object Letter {
        const val KEY_LETTER_DC_ID: Long = 550
    }

    object LetterType{
        const val KEY_IMPORTED = 551
        const val KEY_EXPORTED = 552
        const val KEY_INTERNAL = 553
    }

}