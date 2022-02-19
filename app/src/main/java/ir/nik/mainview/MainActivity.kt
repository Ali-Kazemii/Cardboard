package ir.nik.mainview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ir.nik.cardboard.view.gateway.CardboardBindDataModel
import ir.nik.cardboard.view.gateway.GatewayActivity
import ir.nik.mainview.Constants.KEY_CARDBOARD
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val KEY_RESULT = "result"
        const val LOG_OUT = 123321
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnCardboard.setOnClickListener {
            showCardboard()
        }
    }

    private fun showCardboard() {
        val intent = Intent(this@MainActivity, GatewayActivity::class.java)

        val bundle = Bundle()
        val cardboardModel = CardboardBindDataModel(
            token = token,
            hostName = hostName,
            personnelId = personnelId,
            postId = postId,
            userId = userId,
            imei = imei,
            osVersion = osVersion,
            deviceModel = deviceModel,
            appVersion = appVersion
        )

        bundle.putSerializable(KEY_CARDBOARD, cardboardModel)
        intent.putExtras(bundle)
        cardboardResult.launch(intent)
    }

    private var cardboardResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data

        }
    //TODO: download and authentication error handling
    /*else{
            val data: Intent? = result.data
            val result = data?.getIntExtra(KEY_RESULT, 0)
            Toast.makeText(this@MainActivity, result.toString(), Toast.LENGTH_LONG).show()
        }*/
    }
}