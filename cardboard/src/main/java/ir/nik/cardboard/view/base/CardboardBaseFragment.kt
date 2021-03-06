package ir.nik.cardboard.view.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class CardboardBaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        handleOnClickListeners()
        handleObservers()
        handleError()
    }


    fun logout(){
        (activity as CardboardBaseActivity).logout()
    }


    open fun setup(){}
    open fun handleOnClickListeners(){}
    open fun handleObservers(){}
    open fun handleError(){}
}