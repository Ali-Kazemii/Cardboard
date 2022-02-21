package ir.nik.cardboard.view.cardboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.Const.KEY_CASE_LIST
import ir.nik.cardboard.view.caselist.CaseListActivity
import ir.nik.cardboard.view.caselist.CaseListModel
import ir.nik.cardboard.view.gateway.model.CaseListType
import ir.nik.cardboard.view.gateway.model.CaseType
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CardboardActivity : AppCompatActivity() {

    private val viewModel by viewModel<CardboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardboard)

        viewModel.initDefaultDate()

        val caseType = intent.extras?.getSerializable(KEY_CASE_LIST) as CaseType
        when (caseType) {

            //سایر موارد
            CaseType.DOCUMENT ->
                gotoCardboardFragment(
                    caseType = caseType
                )

            // ارسال شده
            CaseType.SENT ->
                gotoCaseListActivity(
                    id = 0,
                    name = getString(R.string.sent),
                    caseType = caseType
                )
        }

    }

    private fun gotoCardboardFragment(
        caseType: CaseType
    ) {
        replaceFragmentInActivity(
            R.id.container,
            CardBoardFragment { ssId, name ->
                gotoCaseListActivity(
                    id = ssId,
                    name = name,
                    caseType = caseType
                )
            },
            CardBoardFragment.TAG
        )
    }

    private fun gotoCaseListActivity(
        id: Long,
        name: String,
        caseType: CaseType
    ) {
        val intent = Intent(this@CardboardActivity, CaseListActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(
            Const.KEY_CASE_LIST_TYPE,
            CaseListModel().also { model ->
                model.documentStatusId = id
                model.caseName = name
                model.statusReference = CaseListType.KEY_ALL
                model.caseType = caseType
            }
        )
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onBackPressed() {
        this.finish()
    }
}