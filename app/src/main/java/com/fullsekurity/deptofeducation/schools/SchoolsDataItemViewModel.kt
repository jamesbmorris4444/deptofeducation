package com.fullsekurity.deptofeducation.schools

import androidx.databinding.ObservableField
import com.fullsekurity.deptofeducation.activity.Callbacks
import com.fullsekurity.deptofeducation.recyclerview.RecyclerViewItemViewModel
import com.fullsekurity.deptofeducation.repository.storage.SchoolField

class SchoolsDataItemViewModel(private val callbacks: Callbacks) : RecyclerViewItemViewModel<SchoolField>() {

    val name: ObservableField<String> = ObservableField("")
    val cityStateZip: ObservableField<String> = ObservableField("")
    val schoolUrl: ObservableField<String> = ObservableField("")
    val admissionRate: ObservableField<String> = ObservableField("")
    val satScores: ObservableField<String> = ObservableField("")
    val percentComputerDegrees: ObservableField<String> = ObservableField("")

    override fun setItem(item: SchoolField) {
        name.set(item.school.name)
        cityStateZip.set("${item.school.city}, ${item.school.state} ${item.school.zip}")
        schoolUrl.set(item.school.schoolUrl)
        val admissionRateString = String.format("Admission Rate: %d%%", item.admissions.admissionsRate.admissionsRate.toInt())
        admissionRate.set(admissionRateString)
        val satScoresString = String.format("Average SAT Score: %d", item.admissions.satScores.satScores.toInt())
        satScores.set(satScoresString)
        val percentComputerDegreesString = String.format("Computer-Related Degrees: %d%%", item.academics.percentComputerDegrees.percentComputerDegrees.toInt())
        percentComputerDegrees.set(percentComputerDegreesString)
    }

}