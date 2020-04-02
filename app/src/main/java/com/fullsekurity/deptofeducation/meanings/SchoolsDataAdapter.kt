package com.fullsekurity.deptofeducation.meanings

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import com.fullsekurity.deptofeducation.R
import com.fullsekurity.deptofeducation.activity.Callbacks
import com.fullsekurity.deptofeducation.databinding.SchoolsDataListItemBinding
import com.fullsekurity.deptofeducation.recyclerview.RecyclerViewFilterAdapter
import com.fullsekurity.deptofeducation.repository.storage.DIFF_CALLBACK
import com.fullsekurity.deptofeducation.repository.storage.SchoolField
import com.fullsekurity.deptofeducation.repository.storage.SchoolsData
import com.fullsekurity.deptofeducation.ui.UIViewModel
import com.fullsekurity.deptofeducation.utils.DaggerViewModelDependencyInjector
import com.fullsekurity.deptofeducation.utils.ViewModelInjectorModule
import javax.inject.Inject


class SchoolsDataAdapter(val callbacks: Callbacks) : PagedListAdapter<SchoolField, RecyclerViewFilterAdapter.ItemViewHolder<SchoolField, SchoolsDataItemViewModel>>(DIFF_CALLBACK) {

    @Inject
    lateinit var uiViewModel: UIViewModel

    init {
        DaggerViewModelDependencyInjector.builder()
            .viewModelInjectorModule(ViewModelInjectorModule(callbacks.fetchActivity()))
            .build()
            .inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolsDataViewHolder {
        val meaningsListItemBinding: SchoolsDataListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.schools_data_list_item,
            parent,
            false
        )
        val meaningsItemViewModel = SchoolsDataItemViewModel(callbacks)
        meaningsListItemBinding.meaningsItemViewModel = meaningsItemViewModel
        meaningsListItemBinding.uiViewModel = uiViewModel
        return SchoolsDataViewHolder(
            meaningsListItemBinding.root,
            meaningsItemViewModel,
            meaningsListItemBinding
        )
    }

    inner class SchoolsDataViewHolder internal constructor(
        itemView: View,
        viewModel: SchoolsDataItemViewModel,
        viewDataBinding: SchoolsDataListItemBinding
    ) :
        RecyclerViewFilterAdapter.ItemViewHolder<SchoolField, SchoolsDataItemViewModel>(itemView, viewModel, viewDataBinding)

    override fun onBindViewHolder(
        holder: RecyclerViewFilterAdapter.ItemViewHolder<SchoolField, SchoolsDataItemViewModel>,
        position: Int
    ) {
        getItem(position)?.let {
            holder.setItem(it)
        }
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor(uiViewModel.recyclerViewAlternatingColor1))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor(uiViewModel.recyclerViewAlternatingColor2))
        }
    }

}