package ir.nik.cardboard.view.casedetail.timeline

import ir.nik.cardboard.view.casedetail.timeline.CardboardViewType.Companion.ITEM
import ir.nik.cardboard.view.casedetail.timeline.CardboardViewType.Companion.SPECIFIC

class CardboardTypeSpecific(val sender: String, val receiver: String, val referDate: String, val referDescription: String):
    CardboardViewType {
    override fun getViewType(): Int {
        return ITEM
    }

    override fun getTimelineType(): Int {
        return SPECIFIC
    }

    override var isHeader = false
    override var isFooter = false
}