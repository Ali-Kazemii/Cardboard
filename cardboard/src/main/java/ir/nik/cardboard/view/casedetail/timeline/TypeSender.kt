package ir.nik.cardboard.view.casedetail.timeline

import ir.nik.cardboard.view.casedetail.timeline.ViewType.Companion.SENDER

class TypeSender(var sender: String): ViewType {
    override fun getViewType(): Int {
        return ViewType.ITEM
    }

    override fun getTimelineType(): Int {
        return SENDER
    }

    override var isHeader = false
    override var isFooter = false
}