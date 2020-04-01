package com.fullsekurity.deptofeducation.repository.network

import com.fullsekurity.deptofeducation.repository.storage.SchoolField
import com.fullsekurity.deptofeducation.repository.storage.SchoolsData
import com.fullsekurity.deptofeducation.utils.Constants
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("schools")
    fun getSchoolsData(
        @Query(Constants.PAGE_NUMBER) page: Int,
        @Query(Constants.NEWSFEED_KEY) key: String
    ): Flowable<SchoolsResponse>
}

data class SchoolsResponse (
    val metaData: MetaData,
    val results: List<SchoolField>
)

data class MetaData (
    val total: Int,
    val page: Int,
    val perPage: Int
)