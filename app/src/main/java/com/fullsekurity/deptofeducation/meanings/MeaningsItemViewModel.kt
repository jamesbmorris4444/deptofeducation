package com.fullsekurity.deptofeducation.meanings

import androidx.databinding.ObservableField
import com.fullsekurity.deptofeducation.activity.Callbacks
import com.fullsekurity.deptofeducation.logger.LogUtils
import com.fullsekurity.deptofeducation.recyclerview.RecyclerViewItemViewModel
import com.fullsekurity.deptofeducation.repository.network.APIClient
import com.fullsekurity.deptofeducation.repository.storage.SchoolField
import com.fullsekurity.deptofeducation.repository.storage.SchoolsData
import com.google.gson.annotations.SerializedName

class SchoolsDataItemViewModel(private val callbacks: Callbacks) : RecyclerViewItemViewModel<SchoolField>() {

    val name: ObservableField<String> = ObservableField("")
    val city: ObservableField<String> = ObservableField("")
    val state: ObservableField<String> = ObservableField("")
    val zip: ObservableField<String> = ObservableField("")
    val schoolUrl: ObservableField<String> = ObservableField("")

    override fun setItem(item: SchoolField) {
        name.set(item.school.name)
        LogUtils.D(APIClient::class.java.simpleName, LogUtils.FilterTags.withTags(LogUtils.TagFilter.API), String.format("****************************=%s", item.school.city))
        city.set(item.school.city)
        state.set(item.school.state)
        zip.set(item.school.zip)
        schoolUrl.set(item.school.schoolUrl)
    }

}