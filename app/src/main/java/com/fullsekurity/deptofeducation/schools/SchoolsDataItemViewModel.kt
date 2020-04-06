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
        name.set(item.name)
        cityStateZip.set("${item.city}, ${item.state} ${item.zip}")
        schoolUrl.set(item.schoolUrl)
        val admissionRateString = String.format("Admission Rate: %.2f%%", item.admissionsRate * 100)
        admissionRate.set(admissionRateString)
        val satScoresString = String.format("Average SAT Score: %d", item.satScores.toInt())
        satScores.set(satScoresString)
        val percentComputerDegreesString = String.format("Computer-Related Degrees: %.2f%%", item.percentComputerDegrees * 100f)
        percentComputerDegrees.set(percentComputerDegreesString)
    }

}