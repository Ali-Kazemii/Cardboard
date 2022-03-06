package ir.nik.cardboard.view.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.awlrhm.modules.extentions.formatDate
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.CardboardPreferenceConfiguration
import ir.nik.cardboard.data.network.api.CardboardRemoteRepository
import ir.nik.cardboard.data.network.model.base.CardboardBaseResponse
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.request.CardboardAccessTypeListRequest
import ir.nik.cardboard.data.network.model.request.CardboardBaseTypeRequest
import ir.nik.cardboard.data.network.model.request.CardboardReferenceTypeRequest
import ir.nik.cardboard.data.network.model.request.CardboardResponseId
import ir.nik.cardboard.data.network.model.request.CardboardUserDataRequest
import ir.nik.cardboard.data.network.model.response.*
import ir.nik.cardboard.data.network.model.response.CardboardAccessTypeListResponse
import ir.nik.cardboard.data.network.model.response.CardboardBaseTypeResponse
import ir.nik.cardboard.data.network.model.response.CardboardReferenceActionResponse
import ir.nik.cardboard.data.network.model.response.CardboardReferenceTypeResponse
import ir.nik.cardboard.data.network.model.response.CardboardUserListResponse

internal open class CardboardBaseViewModel() : ViewModel() {

    private lateinit var pref: CardboardPreferenceConfiguration
    private lateinit var calendar: PersianCalendar
    private lateinit var remote: CardboardRemoteRepository


    constructor(pref: CardboardPreferenceConfiguration) : this() {
        this.pref = pref
    }

    constructor(
        remote: CardboardRemoteRepository,
        calendar: PersianCalendar
    ) : this() {
        this.remote = remote
        this.calendar = calendar
    }

    constructor(
        pref: CardboardPreferenceConfiguration,
        calendar: PersianCalendar,
        remote: CardboardRemoteRepository
    ) : this() {
        this.calendar = calendar
        this.pref = pref
        this.remote = remote
    }

    constructor(
        pref: CardboardPreferenceConfiguration,
        calendar: PersianCalendar
    ) : this() {
        this.calendar = calendar
        this.pref = pref
    }

    constructor(
        pref: CardboardPreferenceConfiguration,
        remote: CardboardRemoteRepository
    ) : this() {
        this.remote = remote
        this.pref = pref
    }



    /**===========================================================================================*/
    /** ----------------------  Functions -------------------------------------------------------**/
    /**===========================================================================================*/

    val error = MutableLiveData<CardboardBaseResponse?>()
    val errorDelete = MutableLiveData<CardboardBaseResponse?>()
    val responseId = MutableLiveData<CardboardResponseId>()
    val errorPostCaseRefer = MutableLiveData<CardboardBaseResponse?>()
    val userListResponse = MutableLiveData<CardboardUserListResponse>()
    val listAccessTypeResponse = MutableLiveData<CardboardAccessTypeListResponse>()
    val priorityResponse = MutableLiveData<CardboardBaseTypeResponse>()
    val referTypeResponse = MutableLiveData<CardboardReferenceTypeResponse>()
    val referenceActionResponse = MutableLiveData<CardboardReferenceActionResponse>()
    val listProcessResponse = MutableLiveData<CardboardProcessResponse>()
    val referNatureResponse = MutableLiveData<CardboardBaseTypeResponse>()



    fun getReferNature(
        request: CardboardBaseTypeRequest
    ) {
        remote.getReferNature(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardBaseTypeResponse> {
                override fun onDataLoaded(data: CardboardBaseTypeResponse) {
                    referNatureResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getProcessList(
        request: CardboardProcessRequest
    ) {
        remote.getProcessList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardProcessResponse> {
                override fun onDataLoaded(data: CardboardProcessResponse) {
                    listProcessResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            }
        )
    }

    fun postCaseRead(
        request: CardboardCaseReadRequest
    ) {
        remote.postCaseRead(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardBaseResponse> {
                override fun onDataLoaded(data: CardboardBaseResponse) {
                    Log.d("automation", data.message ?: "Success read case")
                }

                override fun onError(response: CardboardBaseResponse?) {
                    response?.message.let {
                        Log.e("automation", it ?: "Error read case")
                    }
                }
            })
    }

    fun postCaseReferWithJson(
        request: CardboardCaseReferWithJsonRequest
    ) {
        remote.postCaseReferWithJson(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardResponseId> {
                override fun onDataLoaded(data: CardboardResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    errorPostCaseRefer.postValue(response)
                }
            })
    }

    fun getReferenceActionList(
        request: CardboardReferenceActionRequest
    ) {
        remote.getReferenceActionList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardReferenceActionResponse> {
                override fun onDataLoaded(data: CardboardReferenceActionResponse) {
                    referenceActionResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getReferTypeList(
        request: CardboardReferenceTypeRequest
    ) {
        remote.getReferenceTypeList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardReferenceTypeResponse> {
                override fun onDataLoaded(data: CardboardReferenceTypeResponse) {
                    referTypeResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            }
        )
    }

    fun getPriority(
        request: CardboardBaseTypeRequest
    ) {
        remote.getPriority(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardBaseTypeResponse> {
                override fun onDataLoaded(data: CardboardBaseTypeResponse) {
                    priorityResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getAccessTypeList(
        request: CardboardAccessTypeListRequest
    ) {
        remote.getAccessTypeList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardAccessTypeListResponse> {
                override fun onDataLoaded(data: CardboardAccessTypeListResponse) {
                    listAccessTypeResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getUserList(
        request: CardboardUserDataRequest
    ) {
        remote.getUserList(
            request,
            object : CardboardRemoteRepository.OnApiCallback<CardboardUserListResponse> {
                override fun onDataLoaded(data: CardboardUserListResponse) {
                    userListResponse.postValue(data)
                }

                override fun onError(response: CardboardBaseResponse?) {
                    error.postValue(response)
                }
            })
    }










    /**===========================================================================================*/
    /** ----------------------  Params -------------------------------------------------------**/
    /**===========================================================================================*/


    var accessToken: String
        get() = pref.accessToken
        set(value) {
            pref.accessToken = value
        }

    var hostName: String
        get() = pref.hostName
        set(value) {
            pref.hostName = value
        }

    var isLogout: Boolean
        get() = pref.isLogout
        set(value) {
            pref.isLogout = value
        }

    var personnelId: Long
        get() = pref.personnelId
        set(value) {
            pref.personnelId = value
        }

    var postId: Long
        get() = pref.postId
        set(value) {
            pref.postId = value
        }

    var userId: Long
        get() = pref.userId
        set(value) {
            pref.userId = value
        }

    val currentDate: String
        get() = formatDate(calendar.persianShortDate)

    var documentStartDate: String
        get() = pref.documentStatDate
        set(value) {
            pref.documentStatDate = value
        }

    var documentEndDate: String
        get() = pref.documentEndDate
        set(value) {
            pref.documentEndDate = value
        }

    var startDate: String
        get() = pref.startDate
        set(value) {
            pref.startDate = value
        }

    var endDate: String
        get() = pref.endDate
        set(value) {
            pref.endDate = value
        }

    val financialYear: Int /*endDate.split('/')[0].toInt()*/
        get() = calendar.persianYear

    var imei: String
        get() = pref.imei
        set(value) {
            pref.imei = value
        }

    var osVersion: String
        get() = pref.osVersion
        set(value) {
            pref.osVersion = value
        }

    var deviceModel: String
        get() = pref.deviceModel
        set(value) {
            pref.deviceModel = value
        }

    var appVersion: String
        get() = pref.appVersion
        set(value) {
            pref.appVersion = value
        }

}