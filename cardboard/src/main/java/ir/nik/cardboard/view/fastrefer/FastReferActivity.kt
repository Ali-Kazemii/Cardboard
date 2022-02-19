package ir.nik.cardboard.view.fastrefer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.nik.cardboard.data.network.model.response.FastReferResponse
import ir.nik.cardboard.view.fastrefer.list.FastReferFragment

internal class FastReferActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fast_refer)

        gotoReferList()
    }

    private fun gotoReferList() {
        replaceFragmentInActivity(
            R.id.container,
            FastReferFragment(
                object: FastReferFragment.OnActionListener{
                    override fun onAdd() {
                        gotoAddFastRefer()
                    }

                    override fun onEdit(model: FastReferResponse.Result) {
                        gotoEditFastRefer(model)
                    }
                }
            ),
            FastReferFragment.TAG
        )
    }

    private fun gotoAddFastRefer() {
        replaceFragmentInActivity(
            R.id.container,
            AddFastReferFragment(),
            AddFastReferFragment.TAG
        )
    }

    private fun gotoEditFastRefer(model: FastReferResponse.Result) {
        replaceFragmentInActivity(
            R.id.container,
            AddFastReferFragment(model),
            AddFastReferFragment.TAG
        )
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()
        else
            this.finish()
    }
}