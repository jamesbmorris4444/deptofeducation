package com.fullsekurity.deptofeducation.repository.storage

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SchoolField(

    @SerializedName(value = "school") var school: SchoolsData

) : Parcelable

@Parcelize
data class SchoolsData(

    @SerializedName(value = "name") var name: String,
    @SerializedName(value = "city") var city: String,
    @SerializedName(value = "state") var state: String,
    @SerializedName(value = "zip") var zip: String,
    @SerializedName(value = "school_url") var schoolUrl: String

) : Parcelable