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

    @SerializedName(value = "school.name") var name: String,
    @SerializedName(value = "school.city") var city: String,
    @SerializedName(value = "school.state") var state: String,
    @SerializedName(value = "school.zip") var zip: String,
    @SerializedName(value = "school.school_url") var schoolUrl: String,
    @SerializedName(value = "2018.admissions.sat_scores.average.by_ope_id") var satScores: Float,
    @SerializedName(value = "2018.academics.program_percentage.computer") var percentComputerDegrees: Float,
    @SerializedName(value = "2018.admissions.admission_rate.by_ope_id") var admissionsRate: Float,
    @SerializedName(value = "id") var id: Int

) : Parcelable

@Parcelize
data class MetaData(

    @SerializedName(value = "total") var total:Int,
    @SerializedName(value = "page") var page:Int,
    @SerializedName(value = "per_page") var perPage: Int

) : Parcelable

var DIFF_CALLBACK: DiffUtil.ItemCallback<SchoolField> = object : DiffUtil.ItemCallback<SchoolField>() {
    override fun areItemsTheSame(oldItem: SchoolField, newItem: SchoolField): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SchoolField, newItem: SchoolField): Boolean {
        return oldItem == newItem
    }
}