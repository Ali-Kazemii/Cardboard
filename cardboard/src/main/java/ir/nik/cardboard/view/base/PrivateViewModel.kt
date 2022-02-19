package ir.nik.cardboard.view.base

import ir.nik.cardboard.data.local.PreferenceConfiguration


internal class PrivateViewModel(
    private val pref: PreferenceConfiguration
): BaseViewModel(pref)