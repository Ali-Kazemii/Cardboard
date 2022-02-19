package ir.nik.cardboard.view.casedetail.timeline

import ir.nik.cardboard.view.casedetail.timeline.ViewType.Companion.ITEM
import ir.nik.cardboard.view.casedetail.timeline.ViewType.Companion.SPECIFIC

class TypeSpecific(val sender: String, val receiver: String, val referDate: String, val referDescription: String):
    ViewType {
    override fun getViewType(): Int {
        return ITEM
    }

    override fun getTimelineType(): Int {
        return SPECIFIC
    }

    override var isHeader = false
    override var isFooter = false
}