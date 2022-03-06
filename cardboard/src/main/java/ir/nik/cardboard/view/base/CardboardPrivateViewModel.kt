package ir.nik.cardboard.view.base

import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration


internal class CardboardPrivateViewModel(
    private val pref: CardboardPreferenceConfiguration
): CardboardBaseViewModel(pref)