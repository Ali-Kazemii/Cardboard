package ir.nik.cardboard.view.gateway

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nik.cardboard.R
import ir.nik.cardboard.di.injectKoin
import ir.nik.cardboard.utils.Const.KEY_CASE_LIST
import ir.nik.cardboard.view.base.PrivateViewModel
import ir.nik.cardboard.view.cardboard.CardboardActivity
import ir.nik.cardboard.view.gateway.model.CardboardBindDataModel
import ir.nik.cardboard.view.gateway.model.CaseType
import ir.nik.cardboard.view.gateway.model.KEY_CARDBOARD
import org.koin.androidx.viewmodel.ext.android.viewModel

class GatewayActivity : AppCompatActivity() {

    private val viewModel by viewModel<PrivateViewModel>()

    companion object {
        const val KEY_RESULT = "result"
        const val LOG_OUT = 123321
    }

    private var caseType: CaseType ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectKoin()
        setContentView(R.layout.activity_gateway)

        if (viewModel.isLogout) {
            viewModel.isLogout = false

            val intent = Intent()
            intent.putExtra(KEY_RESULT, LOG_OUT)
            setResult(Activity.RESULT_OK, intent)
            this@GatewayActivity.finish()

        } else {
            getInitValues()

            gotoCardboard()
        }


    }

    private fun gotoCardboard() {
        val intent = Intent(this@GatewayActivity, CardboardActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(KEY_CASE_LIST, caseType)
        intent.putExtras(bundle)
        startActivity(intent)
    }


    private fun getInitValues() {

        val model = intent.getSerializableExtra(KEY_CARDBOARD) as CardboardBindDataModel

        caseType = model.caseType

        viewModel.accessToken = model.token

        viewModel.hostName = model.hostName

        viewModel.personnelId = model.personnelId

        viewModel.postId = model.postId

        viewModel.userId = model.userId

        viewModel.imei = model.imei

        viewModel.osVersion = model.osVersion

        viewModel.deviceModel = model.deviceModel

        viewModel.appVersion = model.appVersion
    }
}