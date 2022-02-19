package ir.nik.cardboard.data.network.api

import com.google.gson.GsonBuilder
import com.nik.cardboard.BuildConfig
import ir.nik.cardboard.data.network.model.base.BaseResponse
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal abstract class ApiCallback<T : BaseResponse>
    : Callback<T> {

    companion object{
        const val ERROR_RESPONSE = "\"به یکی از دلایل زیر امکان برقراری ارتباط مقدور نمی باشد\\n 1. دستگاه فاقد ارتباط اینترنتی میباشد. \\n 2. سرور در دسترس نمی باشد. \\n 3. خطایی در سرور یا برنامه رخ داده است.\""
        
        const val ERROR_INTERNAL_SERVER = "خطایی در سرور رخ داده است.. با پشتیبانی تماس حاصل فرمایید."

        const val ERROR_UNSECURE_PATH = "برنامه به دلیل ارتباط ناامن، قادر به پاسخگویی نمی باشد.\\n برای استفاده از امکانات اپلیکیشن، اجتناب از راه اندازی برنامه های ناامن ضروری است."

        const val ERROR_CONNECTION = "دستگاه به اینترنت متصل نیست. \\n\\n از برقراری ارتباط اطمینان حاصل کنید یا با حالت آفلاین وارد شوید"
    }
  
  
    private val errorMap = mutableMapOf(
        400 to ERROR_RESPONSE,
        404 to ERROR_RESPONSE,
        406 to ERROR_RESPONSE,
        500 to ERROR_INTERNAL_SERVER
    )

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val body = response.body()
        body?.let {
            when (body.status) {
                true -> response(body, response.headers())
                else -> failure(body)

            }
        } ?: kotlin.run {
            failure(BaseResponse().apply {
                if (!response.isSuccessful) {
                    if (errorMap.containsKey(response.code())) {
                        try {
                            val gson = GsonBuilder().create()
                            val mError = gson.fromJson(
                                response.errorBody()?.string(),
                                BaseResponse::class.java
                            )
                            message = mError?.message ?: errorMap[response.code()]
                            status = mError?.status

                        } catch (e: Exception) { // handle failure to read error
                            status = false
                            message = errorMap[response.code()]
                        }
                    }
                } else {
                    status = false
                    message = ERROR_RESPONSE
                }
            })
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        failure(BaseResponse().apply {
            t.message?.let {
                when {
                    it.contains("java.security.cert.CertPathValidatorException") -> {
                        message = ERROR_UNSECURE_PATH
                    }
                    t.cause.toString().contains("java.net.ConnectException") -> {
                        message = ERROR_CONNECTION
                        isOfflineMode = true
                    }
                    else -> {
                        message = if (BuildConfig.DEBUG)
                            t.message
                        else
                            this.message ?: ERROR_RESPONSE
                    }
                }
            } ?: kotlin.run { ERROR_RESPONSE }
        })
    }

    abstract fun response(response: T, headers: Headers)

    abstract fun failure(response: BaseResponse? = null)
}