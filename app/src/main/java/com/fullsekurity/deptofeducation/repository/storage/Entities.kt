package com.fullsekurity.deptofeducation.repository.storage

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopLevelResponse(

    @SerializedName(value = "metadata") var metaData: MetaData,
    @SerializedName(value = "results") var results: List<SchoolField>

) : Parcelable

@Parcelize
data class SchoolField(

    @SerializedName(value = "school") var school: SchoolsData

) : Parcelable

@Parcelize
data class MetaData(

    @SerializedName(value = "total") var total:Int,
    @SerializedName(value = "page") var page:Int,
    @SerializedName(value = "per_page") var perPage: Int

) : Parcelable

@Parcelize
data class SchoolsData(

    @SerializedName(value = "name") var name: String,
    @SerializedName(value = "city") var city: String,
    @SerializedName(value = "state") var state: String,
    @SerializedName(value = "zip") var zip: String,
    @SerializedName(value = "school_url") var schoolUrl: String

) : Parcelable

var DIFF_CALLBACK: DiffUtil.ItemCallback<SchoolField> = object : DiffUtil.ItemCallback<SchoolField>() {
    override fun areItemsTheSame(oldItem: SchoolField, newItem: SchoolField): Boolean {
        return oldItem.school.name == newItem.school.name
    }

    override fun areContentsTheSame(oldItem: SchoolField, newItem: SchoolField): Boolean {
        return oldItem == newItem
    }
}