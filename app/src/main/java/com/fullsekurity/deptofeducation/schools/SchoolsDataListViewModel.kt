package com.fullsekurity.deptofeducation.schools

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fullsekurity.deptofeducation.activity.Callbacks
import com.fullsekurity.deptofeducation.recyclerview.RecyclerViewViewModel
import com.fullsekurity.deptofeducation.repository.Repository
import com.fullsekurity.deptofeducation.repository.storage.SchoolField
import com.fullsekurity.deptofeducation.ui.UIViewModel
import com.fullsekurity.deptofeducation.utils.DaggerViewModelDependencyInjector
import com.fullsekurity.deptofeducation.utils.ViewModelInjectorModule
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject


class SchoolsDataListViewModelFactory(private val callbacks: Callbacks) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SchoolsDataListViewModel(callbacks) as T
    }
}

class SchoolsDataListViewModel(private val callbacks: Callbacks) : RecyclerViewViewModel(callbacks.fetchActivity().application) {

    override var adapter: SchoolsDataAdapter = SchoolsDataAdapter(callbacks)
    override val itemDecorator: RecyclerView.ItemDecoration? = null
    val listIsVisible: ObservableField<Boolean> = ObservableField(true)
    private lateinit var executor: Executor
    lateinit var schoolsLiveData: LiveData<PagedList<SchoolField>>

    @Inject
    lateinit var uiViewModel: UIViewModel
    @Inject
    lateinit var repository: Repository

    init {
        DaggerViewModelDependencyInjector.builder()
            .viewModelInjectorModule(ViewModelInjectorModule(callbacks.fetchActivity()))
            .build()
            .inject(this)
        init()
    }

    override fun setLayoutManager(): RecyclerView.LayoutManager {
        return object : LinearLayoutManager(getApplication<Application>().applicationContext) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }

            override fun canScrollVertically(): Boolean {
                return true
            }
        }
    }

    fun initialize(view: View) { }

    private fun init() {
        executor = Executors.newFixedThreadPool(5)
        val feedDataFactory = SchoolsDataDataFactory(callbacks)
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(20).build()
        schoolsLiveData = LivePagedListBuilder(feedDataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()
    }

}