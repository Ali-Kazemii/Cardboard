package ir.nik.cardboard.view.base

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ir.nik.cardboard.utils.isSecure
import ir.nik.cardboard.view.gateway.GatewayActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

internal abstract class BaseActivity
    : AppCompatActivity() {

    private val viewModel by viewModel<PrivateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSecure)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        setup()
        handleOnClickListeners()
        handleObservers()
        handleError()
    }

    fun logout() {
        viewModel.isLogout = true
        startActivity(Intent(this, GatewayActivity::class.java))
    }



    open fun setup() {}
    open fun handleError() {}
    open fun handleOnClickListeners() {}
    open fun handleObservers() {}
    open fun showNoConnection(){}
}