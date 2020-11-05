package com.caliente.remote

import android.content.SharedPreferences
import android.webkit.CookieManager
import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiCalienteImpl {

    fun getApiCaliente() : ApiCaliente{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://utils.caliente.mx/pgtools/sms-verification/")
            .client(makeOkHttpClient(loggingInterceptor(BuildConfig.DEBUG)))
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()

        return retrofit.create(ApiCaliente::class.java)
    }

    private fun loggingInterceptor(isDebug: Boolean) = HttpLoggingInterceptor()
        .apply {
            level = if (isDebug) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        //val cookieManager = CookieManager.getInstance()
        //cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)*/

        return OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    val cookieManager: CookieManager = CookieManager.getInstance()
                    val cookies: MutableList<Cookie> = ArrayList()
                    if (cookieManager.getCookie(url.toString()) != null) {
                        val splitCookies: List<String> =
                            cookieManager.getCookie(url.toString()).split(";")
                        for (i in splitCookies.indices) {
                            cookies.add(Cookie.parse(url, splitCookies[i].trim { it <= ' ' })!!)
                        }
                    }
                    return cookies
                }

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>)  {

                }
            })
            //.addInterceptor(AddCookiesInterceptor(sharedPreferences))
            //.addInterceptor(ReceivedCookiesInterceptor(sharedPreferences))
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    /*companion object {

        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder().baseUrl("https://utils.caliente.mx/").client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create()).build()
        }

        *//* fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
    }*//*

        fun provideForecastApi(retrofit: Retrofit): ApiCaliente =
            retrofit.create(ApiCaliente::class.java)
    }*/
}