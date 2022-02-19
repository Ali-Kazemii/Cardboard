package ir.nik.cardboard.di

import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.PreferenceConfiguration
import ir.nik.cardboard.data.network.api.ApiClient
import ir.nik.cardboard.data.network.api.RemoteRepository
import ir.nik.cardboard.view.attachment.AttachmentViewModel
import ir.nik.cardboard.view.base.PrivateViewModel
import ir.nik.cardboard.view.cardboard.CardboardViewModel
import ir.nik.cardboard.view.casedetail.CaseDetailViewModel
import ir.nik.cardboard.view.caselist.CaseListViewModel
import ir.nik.cardboard.view.createletter.CreateLetterViewModel
import ir.nik.cardboard.view.dialogreceiver.ReceiverViewModel
import ir.nik.cardboard.view.fastrefer.FastReferViewModel
import ir.nik.cardboard.view.refer.ReferViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val appModule = module {
    single { PersianCalendar() }
    single {
        PreferenceConfiguration(
            androidContext()
        )
    }
}

val networkModules = module {
    factory { ApiClient(get()).getInterface() }
    single { RemoteRepository(androidContext(), get(), get()) }
}

val viewModelModules = module {
    viewModel { PrivateViewModel(get()) }
    viewModel { CaseDetailViewModel(get(), get(), get()) }
    viewModel { CaseListViewModel(get(), get(), get()) }
    viewModel { ReceiverViewModel(get(), get()) }
    viewModel { CreateLetterViewModel(get(), get(), get()) }
    viewModel { FastReferViewModel(get(), get()) }
    viewModel { ReferViewModel(get(), get(), get()) }
    viewModel { CardboardViewModel(get(), get(), get()) }
    viewModel { AttachmentViewModel(get(), get(), get()) }

}

val listModule = arrayListOf(appModule, viewModelModules, networkModules)

private val koinModules by lazy {
    loadKoinModules(listModule)
}

fun injectKoin() = koinModules