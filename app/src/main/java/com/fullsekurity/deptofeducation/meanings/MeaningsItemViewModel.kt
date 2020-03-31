package com.fullsekurity.deptofeducation.meanings

import androidx.databinding.ObservableField
import com.fullsekurity.deptofeducation.activity.Callbacks
import com.fullsekurity.deptofeducation.recyclerview.RecyclerViewItemViewModel
import com.fullsekurity.deptofeducation.repository.storage.Meaning

class MeaningsItemViewModel(private val callbacks: Callbacks) : RecyclerViewItemViewModel<Meaning>() {

    val source: ObservableField<String> = ObservableField("")
    val author: ObservableField<String> = ObservableField("")
    val title: ObservableField<String> = ObservableField("")
    val description: ObservableField<String> = ObservableField("")
    val url: ObservableField<String> = ObservableField("")
    val urlToImage: ObservableField<String> = ObservableField("")
    val publishedAt: ObservableField<String> = ObservableField("")
    val content: ObservableField<String> = ObservableField("")

    override fun setItem(item: Meaning) {
        source.set(item.source.name)
        author.set(item.author)
        title.set(item.title)
        description.set(item.description)
        url.set(item.url)
        if (item.urlToImage.isEmpty()) {
            urlToImage.set("https://images.barrons.com/im-150385/social")
        } else {
            urlToImage.set(item.urlToImage)
        }
        publishedAt.set(item.publishedAt)
        content.set(item.content)
    }

}