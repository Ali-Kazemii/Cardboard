package ir.nik.cardboard.view.createletter.step

data class StepModel(val id: String, val title: String, val icon: Int, var status: Int = 0, var description: String = "تکمیل نشده")