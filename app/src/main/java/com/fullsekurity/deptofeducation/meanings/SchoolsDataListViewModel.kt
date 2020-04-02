package com.fullsekurity.deptofeducation.meanings

import android.app.Application
import android.view.View
import androidx.arch.core.util.Function
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fullsekurity.deptofeducation.R
import com.fullsekurity.deptofeducation.activity.Callbacks
import com.fullsekurity.deptofeducation.recyclerview.RecyclerViewViewModel
import com.fullsekurity.deptofeducation.repository.Repository
import com.fullsekurity.deptofeducation.repository.storage.SchoolField
import com.fullsekurity.deptofeducation.ui.UIViewModel
import com.fullsekurity.deptofeducation.utils.DaggerViewModelDependencyInjector
import com.fullsekurity.deptofeducation.utils.Utils
import com.fullsekurity.deptofeducation.utils.ViewModelInjectorModule
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
    val submitVisible: ObservableField<Int> = ObservableField(View.GONE)
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

    fun initialize(view: View) {
        val textInputLayout: TextInputLayout = view.findViewById(R.id.edit_text_input_name)
        val textInputEditText: TextInputEditText = view.findViewById(R.id.edit_text_input_name_editText)
        textInputLayout.setHintTextAppearance(uiViewModel.editTextDisplayModifyHintStyle)
        textInputEditText.requestFocus()
        Utils.showKeyboard(textInputEditText)
    }

    // observable used for two-way donations binding. Values set into this field will show in view.
    // Text typed into EditText in view will be stored into this field after each character is typed.
    var editTextNameInput: ObservableField<String> = ObservableField("")
    fun onTextNameChanged(key: CharSequence, start: Int, before: Int, count: Int) {
        if (key.isEmpty()) {
            submitVisible.set(View.GONE)
        } else {
            submitVisible.set(View.VISIBLE)
        }
        // within "string", the "count" characters beginning at index "start" have just replaced old text that had length "before"
    }
    var hintTextName: ObservableField<String> = ObservableField(getApplication<Application>().applicationContext.getString(R.string.meanings_hint_text))
    var editTextNameVisibility: ObservableField<Int> = ObservableField(View.VISIBLE)

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

//    fun onSearchClicked(view: View) {
//        val enteredText: String = editTextNameInput.get() ?: ""
//        if (enteredText == "s") {
//            callbacks.fetchActivity().startPretendLongRunningTask()
//        } else if (enteredText == "p") {
//            callbacks.fetchActivity().pausePretendLongRunningTask()
//        } else if (enteredText == "r") {
//            callbacks.fetchActivity().resumePretendLongRunningTask()
//        } else {
//            Utils.hideKeyboard(view)
//            val progressBar = callbacks.fetchActivity().getMainProgressBar()
//            progressBar.visibility = View.VISIBLE
//            repository.getUrbanDictionarySchoolsData(enteredText, this::showSchoolsData)
//        }
//    }
//
//    private fun showSchoolsData(meaningsList: List<SchoolField>?) {
//        val progressBar = callbacks.fetchActivity().getMainProgressBar()
//        progressBar.visibility = View.GONE
//        if (meaningsList == null) {
//            listIsVisible.set(false)
//        } else {
//            listIsVisible.set(meaningsList.isNotEmpty())
//            adapter.addAll(meaningsList)
//        }
//    }

}