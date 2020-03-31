package com.fullsekurity.deptofeducation.utils

import androidx.lifecycle.ViewModelProvider
import com.fullsekurity.deptofeducation.activity.MainActivity
import com.fullsekurity.deptofeducation.meanings.MeaningsAdapter
import com.fullsekurity.deptofeducation.meanings.MeaningsFragment
import com.fullsekurity.deptofeducation.meanings.MeaningsListViewModel
import com.fullsekurity.deptofeducation.modal.StandardModal
import com.fullsekurity.deptofeducation.repository.Repository
import com.fullsekurity.deptofeducation.ui.UIViewModel
import com.fullsekurity.deptofeducation.ui.UIViewModelFactory

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [MapperInjectorModule::class])
interface MapperDependencyInjector {
    fun inject(viewModel: UIViewModel)
}

@Singleton
@Component(modules = [ViewModelInjectorModule::class])
interface ViewModelDependencyInjector {
    fun inject(fragment: MeaningsFragment)
    fun inject(modal: StandardModal)
    fun inject(viewModel: MeaningsListViewModel)
    fun inject(activity: MainActivity)
    fun inject(adapter: MeaningsAdapter)
}

@Module
class MapperInjectorModule {
    @Provides
    @Singleton
    fun colorMapperProvider() : ColorMapper {
        val colorMapper = ColorMapper()
        colorMapper.initialize()
        return colorMapper
    }
    @Provides
    @Singleton
    fun textSizeMapperProvider() : TextSizeMapper {
        val textSizeMapper = TextSizeMapper()
        textSizeMapper.initialize()
        return textSizeMapper
    }
    @Provides
    @Singleton
    fun typefaceMapperProvider() : TypefaceMapper {
        val typefaceMapper = TypefaceMapper()
        typefaceMapper.initialize()
        return typefaceMapper
    }
}

@Module
class ViewModelInjectorModule(val activity: MainActivity) {
    @Provides
    @Singleton
    fun uiViewModelProvider() : UIViewModel {
        return ViewModelProvider(activity, UIViewModelFactory(activity.application)).get(UIViewModel::class.java)
    }
    @Provides
    @Singleton
    fun repositoryProvider() : Repository {
        return activity.repository
    }

}