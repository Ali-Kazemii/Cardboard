package ir.nik.cardboard.view.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.awlrhm.modules.extentions.formatDate
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nik.cardboard.data.local.PreferenceConfiguration
import ir.nik.cardboard.data.network.api.RemoteRepository
import ir.nik.cardboard.data.network.model.base.BaseResponse
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.request.AccessTypeListRequest
import ir.nik.cardboard.data.network.model.request.BaseTypeRequest
import ir.nik.cardboard.data.network.model.request.ReferenceTypeRequest
import ir.nik.cardboard.data.network.model.request.ResponseId
import ir.nik.cardboard.data.network.model.request.UserDataRequest
import ir.nik.cardboard.data.network.model.response.*
import ir.nik.cardboard.data.network.model.response.AccessTypeListResponse
import ir.nik.cardboard.data.network.model.response.BaseTypeResponse
import ir.nik.cardboard.data.network.model.response.ReferenceActionResponse
import ir.nik.cardboard.data.network.model.response.ReferenceTypeResponse
import ir.nik.cardboard.data.network.model.response.UserListResponse

internal open class BaseViewModel() : ViewModel() {

    private lateinit var pref: PreferenceConfiguration
    private lateinit var calendar: PersianCalendar
    private lateinit var remote: RemoteRepository


    constructor(pref: PreferenceConfiguration) : this() {
        this.pref = pref
    }

    constructor(
        remote: RemoteRepository,
        calendar: PersianCalendar
    ) : this() {
        this.remote = remote
        this.calendar = calendar
    }

    constructor(
        pref: PreferenceConfiguration,
        calendar: PersianCalendar,
        remote: RemoteRepository
    ) : this() {
        this.calendar = calendar
        this.pref = pref
        this.remote = remote
    }

    constructor(
        pref: PreferenceConfiguration,
        calendar: PersianCalendar
    ) : this() {
        this.calendar = calendar
        this.pref = pref
    }

    constructor(
        pref: PreferenceConfiguration,
        remote: RemoteRepository
    ) : this() {
        this.remote = remote
        this.pref = pref
    }



    /**===========================================================================================*/
    /** ----------------------  Functions -------------------------------------------------------**/
    /**===========================================================================================*/

    val error = MutableLiveData<BaseResponse?>()
    val errorDelete = MutableLiveData<BaseResponse?>()
    val responseId = MutableLiveData<ResponseId>()
    val errorPostCaseRefer = MutableLiveData<BaseResponse?>()
    val userListResponse = MutableLiveData<UserListResponse>()
    val listAccessTypeResponse = MutableLiveData<AccessTypeListResponse>()
    val priorityResponse = MutableLiveData<BaseTypeResponse>()
    val referTypeResponse = MutableLiveData<ReferenceTypeResponse>()
    val referenceActionResponse = MutableLiveData<ReferenceActionResponse>()
    val listProcessResponse = MutableLiveData<ProcessResponse>()
    val referNatureResponse = MutableLiveData<BaseTypeResponse>()




    fun getReferNature(
        request: BaseTypeRequest
    ) {
        remote.getReferNature(
            request,
            object : RemoteRepository.OnApiCallback<BaseTypeResponse> {
                override fun onDataLoaded(data: BaseTypeResponse) {
                    referNatureResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getProcessList(
        request: ProcessRequest
    ) {
        remote.getProcessList(
            request,
            object : RemoteRepository.OnApiCallback<ProcessResponse> {
                override fun onDataLoaded(data: ProcessResponse) {
                    listProcessResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            }
        )
    }

    fun postCaseRead(
        request: CaseReadRequest
    ) {
        remote.postCaseRead(
            request,
            object : RemoteRepository.OnApiCallback<BaseResponse> {
                override fun onDataLoaded(data: BaseResponse) {
                    Log.d("automation", data.message ?: "Success read case")
                }

                override fun onError(response: BaseResponse?) {
                    response?.message.let {
                        Log.e("automation", it ?: "Error read case")
                    }
                }
            })
    }

    fun postCaseReferWithJson(
        request: CaseReferWithJsonRequest
    ) {
        remote.postCaseReferWithJson(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    errorPostCaseRefer.postValue(response)
                }
            })
    }

    fun getReferenceActionList(
        request: ReferenceActionRequest
    ) {
        remote.getReferenceActionList(
            request,
            object : RemoteRepository.OnApiCallback<ReferenceActionResponse> {
                override fun onDataLoaded(data: ReferenceActionResponse) {
                    referenceActionResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getReferTypeList(
        request: ReferenceTypeRequest
    ) {
        remote.getReferenceTypeList(
            request,
            object : RemoteRepository.OnApiCallback<ReferenceTypeResponse> {
                override fun onDataLoaded(data: ReferenceTypeResponse) {
                    referTypeResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            }
        )
    }

    fun getPriority(
        request: BaseTypeRequest
    ) {
        remote.getPriority(
            request,
            object : RemoteRepository.OnApiCallback<BaseTypeResponse> {
                override fun onDataLoaded(data: BaseTypeResponse) {
                    priorityResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getAccessTypeList(
        request: AccessTypeListRequest
    ) {
        remote.getAccessTypeList(
            request,
            object : RemoteRepository.OnApiCallback<AccessTypeListResponse> {
                override fun onDataLoaded(data: AccessTypeListResponse) {
                    listAccessTypeResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getUserList(
        request: UserDataRequest
    ) {
        remote.getUserList(
            request,
            object : RemoteRepository.OnApiCallback<UserListResponse> {
                override fun onDataLoaded(data: UserListResponse) {
                    userListResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
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