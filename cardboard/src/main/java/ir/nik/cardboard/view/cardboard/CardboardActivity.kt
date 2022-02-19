package ir.nik.cardboard.view.cardboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.view.caselist.CaseListActivity
import ir.nik.cardboard.view.caselist.CaseListModel
import ir.nik.cardboard.view.caselist.CaseType
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CardboardActivity : AppCompatActivity() {

    private val viewModel by viewModel<CardboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardboard)

        viewModel.initDefaultDate()

        gotoCardboardFragment()
    }

    private fun gotoCardboardFragment() {
        replaceFragmentInActivity(
            R.id.container,
            CardBoardFragment { ssId, name ->
                gotoCaseListActivity(
                    ssId,
                    name
                )
            },
            CardBoardFragment.TAG
        )
    }

    private fun gotoCaseListActivity(
        id: Long,
        name: String
    ) {
        val intent = Intent(this@CardboardActivity, CaseListActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(
            Const.KEY_CASE_LIST_TYPE,
            CaseListModel().also { model ->
                model.documentStatusId = id
                model.caseName = name
                model.statusReference = Const.StatusReference.KEY_ALL
                model.caseType =    CaseType.DOCUMENT
            }
        )
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onBackPressed() {
        this.finish()
    }
}