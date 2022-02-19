package ir.nik.cardboard.data.network.api

import ir.nik.cardboard.data.local.PreferenceConfiguration

internal class ApiClient(
    private val pref: PreferenceConfiguration
) {
    fun getInterface(): ApiInterface = WebServiceGateway(
        pref
    )
        .retrofit
        .create(ApiInterface::class.java)
}