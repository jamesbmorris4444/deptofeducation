package com.fullsekurity.deptofeducation.repository.network

import com.fullsekurity.deptofeducation.logger.LogUtils
import com.fullsekurity.deptofeducation.logger.LogUtils.TagFilter.API
import com.fullsekurity.deptofeducation.utils.Constants.EDUC_DEPT_LIST_CLASS_TYPE
import com.fullsekurity.deptofeducation.utils.Constants.SCHOOLS_DATA_BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    val client: APIInterface
        get() {
            val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    LogUtils.D(APIClient::class.java.simpleName, LogUtils.FilterTags.withTags(API), String.format("okHttp logging interceptor=%s", message))
                }
            })
            interceptor.level = HttpLoggingInterceptor.Level.BODY  // BASIC or BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val gson = GsonBuilder()
                .registerTypeAdapter(EDUC_DEPT_LIST_CLASS_TYPE,
                    SchoolsDataJsonDeserializer()
                )
                .create()
            val builder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(SCHOOLS_DATA_BASE_URL)
            return builder.build().create(APIInterface::class.java)
        }

}
