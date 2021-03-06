package ir.nik.cardboard.view.cardboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.Const.KEY_CASE_LIST
import ir.nik.cardboard.view.caselist.CardboardCaseListActivity
import ir.nik.cardboard.view.caselist.CardboardCaseListModel
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
            // ارسال شده
            CaseType.SENT ->
                gotoCaseListActivity(
                    ssId = 0,
                    name = getString(R.string.sent),
                    caseType = caseType
                )

            //سایر موارد
           else ->
                gotoCardboardFragment(
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
                    ssId = ssId,
                    name = name,
                    caseType = caseType
                )
            },
            CardBoardFragment.TAG
        )
    }

    private fun gotoCaseListActivity(
        ssId: Long,
        name: String,
        caseType: CaseType
    ) {
        val intent = Intent(this@CardboardActivity, CardboardCaseListActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(
            Const.KEY_CASE_LIST_TYPE,
            CardboardCaseListModel().also { model ->
                model.documentSsId = ssId
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