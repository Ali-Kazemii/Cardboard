package ir.nik.cardboard.view.casedetail.timeline

import ir.nik.cardboard.view.casedetail.timeline.ViewType.Companion.LINE

class LineModel: ViewType {
    override fun getViewType(): Int {
        return LINE
    }

    override fun getTimelineType(): Int {return 0}

    override var isHeader = false
    override var isFooter = false
}