package ir.nik.cardboard.data.network.api

import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration

internal class CardboardApiClient(
    private val pref: CardboardPreferenceConfiguration
) {
    fun getInterface(): CardboardApiInterface = CardboardWebServiceGateway(
        pref
    )
        .retrofit
        .create(CardboardApiInterface::class.java)
}