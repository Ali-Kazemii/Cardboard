package ir.nik.cardboard.view.casedetail.timeline

import ir.nik.cardboard.view.casedetail.timeline.ViewType.Companion.CREATOR
import ir.nik.cardboard.view.casedetail.timeline.ViewType.Companion.ITEM

class TypeCreator(val creator: String): ViewType {
    override fun getViewType(): Int {
        return ITEM
    }

    override fun getTimelineType(): Int {
        return CREATOR
    }

    override var isHeader = false
    override var isFooter = false
}