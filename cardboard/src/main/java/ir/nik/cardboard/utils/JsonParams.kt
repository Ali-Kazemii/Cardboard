package ir.nik.cardboard.utils

import com.google.gson.Gson
import ir.nik.cardboard.data.network.model.utt.UTTWFSModel
import org.json.JSONArray
import org.json.JSONObject

internal fun commercialDocumentByCustomerId(
    customerId: Long,
    search: String
): String{
    return "{\\\"ExpressionSearch\\\":\\\"${search}\\\",\\\"TBL_CustomerIDs_fk\\\":\\\"$customerId\\\"}"
}


internal fun getLetterExtraInformationJson(
    letterId: Long,
    list: List<String>
): String {
    val listJson: MutableList<String> = mutableListOf()
    list.forEachIndexed { index, keyword ->
        listJson.add("{\"RowNum\":${index},\"OAS_LpID\":0,\"OAS_LetterID_fk\":${letterId},\"OAS_LptID_fk\":1,\"OAS_LpValue\":\"${keyword}\"}")
    }
    val json = StringBuilder()
    json.append("[")
    listJson.forEachIndexed { index, str ->
        json.append(str)
        if (index + 1 != listJson.size)
            json.append(",")
    }
    json.append("]")
    return json.toString()
}

internal fun projectSpacialListJson(
    search: String,
    customerId: Long
): String{
    return "{\\\"TBL_CustomerIDs_fk\\\":\\\"$customerId\\\",\\\"ExpressionSearch\\\":\\\"${search}\\\"}"
}


internal fun customerAccountByCustomerIdJson(
    search: String,
    customerId: Long
): String{
    return "{\\\"ExpressionSearch\\\":\\\"${search}\\\",\\\"TBL_CustomerIDs_fk\\\":\\\"$customerId\\\"}"
}

internal fun contractByCustomerIdJson(
    customerId: Long,
    search: String
): String {
    return "{\\\"ExpressionSearch\\\":\\\"${search}\\\",\\\"TBL_CustomerIDs_fk\\\":\\\"$customerId\\\"}"
}

internal fun letterInformationJson(
    formId: Int,
    search: String
): String {
    return "{\\\"TBL_FormIDs_fk\\\":\\\"$formId\\\",\\\"ExpressionSearch\\\":\\\"${search}\\\"}"
}

internal fun documentSendReceiveMethodJson(): String{
    return "{\\\"WFS_DtParentIDs_fk\\\":\\\"10\\\"}"
}

internal fun baseTypeJson(
    type: Int
): String {
    return "{\\\"WFS_WfsBaseParentIDs_fk\\\":\\\"$type\\\"}"
}

internal fun caseLinkedJson(
    wfsCaseId: Long
) :String{
    return "{\\\"WFS_CaseIDs_fk\\\":\\\"$wfsCaseId\\\"}"
}

internal fun getLetterLinkedJson(
    wfsCaseLinkedId: Long,
    wfsClTypeId: Long
): String {
    return "[{\"RowNum\":1,\"WFS_ClID\":0,\"WFS_CaseLinkedID_fk\":${wfsCaseLinkedId},\"WFS_ClTypeID_fk\":${wfsClTypeId}}]"
}

internal fun documentRelationTypeJson() :String{
    return "{\\\"WFS_WfsBaseParentIDs_fk\\\":\\\"6\\\"}"
}

internal fun caseReferralListByWFSCaseId(
    caseId: Long
): String{
    return "{\\\"WFS_CaseIDs_fk\\\":$caseId}"
}


internal fun convertUTTWFSModelToJson(list: MutableList<UTTWFSModel>): String {
    val jsonArray = JSONArray()
    list.forEachIndexed { index, _ ->
        list[index].rowNum = index + 1
        val str = Gson().toJson(list[index])
        val json = JSONObject(str)
        jsonArray.put(json)
    }
    return jsonArray.toString()
}

internal fun referenceActionJson(): String {
    return "{\\\"WFS_RaParentIDs_fk\\\":\\\"0\\\"}"
}

internal fun referTypeJson(): String {
    return "{\\\"WFS_RtParentIDs_fk\\\":\\\"0\\\"}"
}

internal fun caseDetailBodyJson(): String{
    return "{\\\"ReportName\\\":\\\"\\\",\\\"ReportKind\\\":\\\"0\\\"}"
}

internal fun historyReportJson(
    wfsCaseId: Long?
): String{
    return "{\\\"WFS_CaseIDs_fk\\\":\\\"$wfsCaseId\\\",\\\"ReportName\\\":\\\"\\\",\\\"ReportKind\\\":\\\"0\\\"}"
}

internal fun getControlReferParameterJson(
    processId: Long
): String {
    return "{\\\"WFS_ProcessID\\\":\\\"$processId\\\",\\\"UserFastReferralStatus\\\":\\\"1\\\"}"
}

internal fun caseReferHistoryJson(
    wfsCaseId: Long
): String {
    return "{\\\"WFS_CaseIDs_fk\\\":${wfsCaseId}}"
}

internal fun cardboardInformationJson(
    startRange: String,
    endRange: String,
    documentStatusId: Long, // its same as CaseReferralStatus refer to سرویس های جدید معادل_14000822
    wfsProcessId: Long,
    search: String
): String {
    return "{\\\"StartRange\\\":\\\"${startRange}\\\",\\\"EndRange\\\":\\\"${endRange}\\\",\\\"CaseReferralStatus\\\":$documentStatusId,\\\"WFS_ProcessIDs_fk\\\":$wfsProcessId,\\\"ExpressionSearch\\\":\\\"${search}\\\"}"
}

internal fun processJson(
    documentStatusId: Long // its same as CaseReferralStatus refer to سرویس های جدید معادل_14000822
): String {
    return "{\\\"CaseReferralStatus\\\":\\\"$documentStatusId\\\"}"
}

internal fun receiverJson(
    wfsCrId: Long,
    wfsPsNextId: Long
): String {
    return "{\\\"WFS_CrIDs_fk\\\":\\\"$wfsCrId\\\",\\\"WFS_PsIDs_fk\\\":\\\"$wfsPsNextId\\\",\\\"WFS_CaseTableIDs\\\":\\\"12345\\\"}"
}

internal fun fastReferJson(
    userId: Long
): String {
    return "{\\\"TBL_UserIDs_fk\\\":\\\"$userId\\\"}"
}

internal fun userListJson(
    utIds: Long,
    search: String
): String {
    return "{\\\"TBL_UtIDs_fk\\\":\\\"$utIds\\\",\\\"ExpressionSearch\\\":\\\"${search}\\\"}"
}

internal fun processStepJson(
    wfsCrId: Long,
    processId: Long,
    processTableName: String
): String {
    return "{\\\"WFS_ProcessIDs_fk\\\":\\\"$processId\\\",\\\"WFS_CrIDs_fk\\\":\\\"$wfsCrId\\\",\\\"WFS_ProcessTableName\\\":\\\"${processTableName}\\\"}"
}

internal fun cardboardListJson(
    startRange: String,
    endRange: String
): String {
    return "{\\\"StartRange\\\":\\\"${startRange}\\\",\\\"EndRange\\\":\\\"${endRange}\\\"}"
}

internal fun attachmentJson(
    relatedTableId: Long,
    dcIds: Long
): String{
    return "{\\\"DMS_DocumentRelatedTableId\\\":\\\"${relatedTableId}\\\",\\\"DMS_DcIDs_fk\\\":\\\"${dcIds}\\\"}"
}













