package com.fullsekurity.deptofeducation.schools

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.fullsekurity.deptofeducation.R
import com.fullsekurity.deptofeducation.activity.Callbacks
import com.fullsekurity.deptofeducation.activity.MainActivity
import com.fullsekurity.deptofeducation.databinding.SchoolsDataFragmentBinding
import com.fullsekurity.deptofeducation.repository.storage.SchoolField
import com.fullsekurity.deptofeducation.ui.UIViewModel
import com.fullsekurity.deptofeducation.utils.Constants
import com.fullsekurity.deptofeducation.utils.DaggerViewModelDependencyInjector
import com.fullsekurity.deptofeducation.utils.ViewModelInjectorModule
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class SchoolsDataFragment : Fragment(), Callbacks {

    private lateinit var meaningsListViewModel: SchoolsDataListViewModel
    private lateinit var binding: SchoolsDataFragmentBinding
    private lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance(): SchoolsDataFragment {
            return SchoolsDataFragment()
        }
    }

    @Inject
    lateinit var uiViewModel: UIViewModel

    override fun onAttach(context: Context) {
        DaggerViewModelDependencyInjector.builder()
            .viewModelInjectorModule(ViewModelInjectorModule(activity as MainActivity))
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbar.title = Constants.SCHOOLS_DATA_TITLE
        meaningsListViewModel.initialize(binding.root)
        meaningsListViewModel.schoolsLiveData?.observe(this, Observer<PagedList<SchoolField>> { pagedList ->
            meaningsListViewModel.adapter.submitList(pagedList)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.schools_data_fragment, container, false) as SchoolsDataFragmentBinding
        binding.lifecycleOwner = this
        meaningsListViewModel = ViewModelProvider(this, SchoolsDataListViewModelFactory(this)).get(SchoolsDataListViewModel::class.java)
        binding.meaningsListViewModel = meaningsListViewModel
        binding.uiViewModel = uiViewModel
        uiViewModel.currentTheme = (activity as MainActivity).currentTheme
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun fetchActivity(): MainActivity {
        return if (::mainActivity.isInitialized) {
            mainActivity
        } else {
            activity as MainActivity
        }
    }

    override fun fetchRootView(): View {
        return binding.root
    }

    override fun fetchmeaningsListViewModel() : SchoolsDataListViewModel? {
        return meaningsListViewModel
    }

}