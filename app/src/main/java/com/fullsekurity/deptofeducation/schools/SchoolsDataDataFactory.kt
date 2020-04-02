package com.fullsekurity.deptofeducation.schools

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.fullsekurity.deptofeducation.activity.Callbacks
import com.fullsekurity.deptofeducation.repository.storage.SchoolField


class SchoolsDataDataFactory(private val callbacks: Callbacks) : DataSource.Factory<Int, SchoolField>() {
    private val mutableLiveData: MutableLiveData<DataSource<Int, SchoolField?>> = MutableLiveData()
    private var feedDataSource: DataSource<Int, SchoolField?>? = null
    override fun create(): DataSource<Int, SchoolField?> {
        feedDataSource = SchoolsDataDataSource(callbacks)
        mutableLiveData.postValue(feedDataSource)
        return feedDataSource as DataSource<Int, SchoolField?>
    }

    fun getMutableLiveData(): MutableLiveData<DataSource<Int, SchoolField?>> {
        return mutableLiveData
    }

}