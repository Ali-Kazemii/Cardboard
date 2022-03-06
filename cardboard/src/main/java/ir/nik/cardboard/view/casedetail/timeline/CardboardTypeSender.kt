package ir.nik.cardboard.view.casedetail.timeline

import ir.nik.cardboard.view.casedetail.timeline.CardboardViewType.Companion.SENDER

class CardboardTypeSender(var sender: String): CardboardViewType {
    override fun getViewType(): Int {
        return CardboardViewType.ITEM
    }

    override fun getTimelineType(): Int {
        return SENDER
    }

    override var isHeader = false
    override var isFooter = false
}