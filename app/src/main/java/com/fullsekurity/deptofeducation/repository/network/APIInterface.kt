package com.fullsekurity.deptofeducation.repository.network

import com.fullsekurity.deptofeducation.repository.storage.TopLevelResponse
import com.fullsekurity.deptofeducation.utils.Constants
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("schools")
    fun getSchoolsData(
        @Query(Constants.PAGE_NUMBER) page: Int,
        @Query(Constants.EDUC_DEPT_FIELDS_KEY) fields: String,
        @Query(Constants.EDUC_DEPT_KEY) key: String
    ): Flowable<TopLevelResponse>
}