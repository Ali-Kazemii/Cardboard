package ir.nik.cardboard.view.caselist

import java.io.Serializable

class CaseListModel: Serializable{
    var documentStatusId: Long?= null
    var caseName: String?= null
    var statusReference: Int = 0
    var caseType: CaseType?= null
    //for cartable
    var rtId: Long = 0
    //for archive
    var archiveId: Long = 0
}