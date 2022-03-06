package ir.nik.cardboard.view.fastrefer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.nik.cardboard.data.network.model.response.CardboardFastReferResponse
import ir.nik.cardboard.view.fastrefer.list.CardboardFastReferFragment

internal class CardboardFastReferActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fast_refer_cardboard)

        gotoReferList()
    }

    private fun gotoReferList() {
        replaceFragmentInActivity(
            R.id.container,
            CardboardFastReferFragment(
                object: CardboardFastReferFragment.OnActionListener{
                    override fun onAdd() {
                        gotoAddFastRefer()
                    }

                    override fun onEdit(model: CardboardFastReferResponse.Result) {
                        gotoEditFastRefer(model)
                    }
                }
            ),
            CardboardFastReferFragment.TAG
        )
    }

    private fun gotoAddFastRefer() {
        replaceFragmentInActivity(
            R.id.container,
            CardboardAddFastReferFragment(),
            CardboardAddFastReferFragment.TAG
        )
    }

    private fun gotoEditFastRefer(model: CardboardFastReferResponse.Result) {
        replaceFragmentInActivity(
            R.id.container,
            CardboardAddFastReferFragment(model),
            CardboardAddFastReferFragment.TAG
        )
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()
        else
            this.finish()
    }
}