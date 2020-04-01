package com.fullsekurity.deptofeducation.activity

import android.view.View
import com.fullsekurity.deptofeducation.meanings.SchoolsDataListViewModel

interface Callbacks {
    fun fetchActivity(): MainActivity
    fun fetchRootView() : View
    fun fetchmeaningsListViewModel() : SchoolsDataListViewModel?
}