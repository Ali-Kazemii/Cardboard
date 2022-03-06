package ir.nik.cardboard.view.caselist

import ir.nik.cardboard.view.gateway.model.CaseListType
import ir.nik.cardboard.view.gateway.model.CaseType
import java.io.Serializable

class CardboardCaseListModel: Serializable{
    var documentStatusId: Long?= null
    var caseName: String?= null
    var statusReference: CaseListType = CaseListType.KEY_ALL
    var caseType: CaseType = CaseType.DOCUMENT
    //for cartable
    var rtId: Long = 0
    //for archive
    var archiveId: Long = 0
}