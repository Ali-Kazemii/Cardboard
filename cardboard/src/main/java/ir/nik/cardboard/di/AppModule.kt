package ir.nik.cardboard.di

import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration
import ir.nik.cardboard.data.network.api.CardboardApiClient
import ir.nik.cardboard.data.network.api.CardboardRemoteRepository
import ir.nik.cardboard.view.attachment.CardboardAttachmentViewModel
import ir.nik.cardboard.view.base.CardboardPrivateViewModel
import ir.nik.cardboard.view.cardboard.CardboardViewModel
import ir.nik.cardboard.view.casedetail.CardboardCaseDetailViewModel
import ir.nik.cardboard.view.caselist.CardboardCaseListViewModel
import ir.nik.cardboard.view.createletter.CreateLetterViewModel
import ir.nik.cardboard.view.dialogreceiver.CardboardReceiverViewModel
import ir.nik.cardboard.view.fastrefer.CardboardFastReferViewModel
import ir.nik.cardboard.view.refer.CardboardReferViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val appModule = module {
    single { PersianCalendar() }
    single {
        CardboardPreferenceConfiguration(
            androidContext()
        )
    }
}

val networkModules = module {
    factory { CardboardApiClient(get()).getInterface() }
    single { CardboardRemoteRepository(androidContext(), get(), get()) }
}

val viewModelModules = module {
    viewModel { CardboardPrivateViewModel(get()) }
    viewModel { CardboardCaseDetailViewModel(get(), get(), get()) }
    viewModel { CardboardCaseListViewModel(get(), get(), get()) }
    viewModel { CardboardReceiverViewModel(get(), get()) }
    viewModel { CreateLetterViewModel(get(), get(), get()) }
    viewModel { CardboardFastReferViewModel(get(), get()) }
    viewModel { CardboardReferViewModel(get(), get(), get()) }
    viewModel { CardboardViewModel(get(), get(), get()) }
    viewModel { CardboardAttachmentViewModel(get(), get(), get()) }
}

val listModule = arrayListOf(appModule, viewModelModules, networkModules)

private val koinModules by lazy {
    loadKoinModules(listModule)
}

fun injectKoin() = koinModules