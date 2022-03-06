package ir.nik.cardboard.view.casedetail.timeline

import ir.nik.cardboard.view.casedetail.timeline.CardboardViewType.Companion.CREATOR
import ir.nik.cardboard.view.casedetail.timeline.CardboardViewType.Companion.ITEM

class CardboardTypeCreator(val creator: String): CardboardViewType {
    override fun getViewType(): Int {
        return ITEM
    }

    override fun getTimelineType(): Int {
        return CREATOR
    }

    override var isHeader = false
    override var isFooter = false
}