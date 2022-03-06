package ir.nik.cardboard.view.casedetail.timeline

interface CardboardViewType {
    fun getViewType():Int
    fun getTimelineType(): Int

    var isHeader: Boolean
    var isFooter: Boolean

    companion object {
        val LINE = 2
        val ITEM = 3

        val SPECIFIC = 5
        val SENDER = 6
        val CREATOR = 7
    }
}