package com.fullsekurity.deptofeducation.meanings

import android.app.SearchManager.QUERY
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.fullsekurity.deptofeducation.R
import com.fullsekurity.deptofeducation.activity.Callbacks
import com.fullsekurity.deptofeducation.activity.MainActivity
import com.fullsekurity.deptofeducation.logger.LogUtils
import com.fullsekurity.deptofeducation.modal.StandardModal
import com.fullsekurity.deptofeducation.repository.network.APIClient
import com.fullsekurity.deptofeducation.repository.network.APIInterface
import com.fullsekurity.deptofeducation.repository.storage.SchoolField
import com.fullsekurity.deptofeducation.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SchoolsDataDataSource(private val callbacks: Callbacks) : PageKeyedDataSource<Int, SchoolField?>() {

    private val apiInterface: APIInterface = APIClient.client

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, SchoolField?>) {
        var disposable: Disposable? = null
        disposable = apiInterface.getSchoolsData(1, Constants.NEWSFEED_API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .timeout(15L, TimeUnit.SECONDS)
            .subscribe({ meaningsResponse ->
                disposable?.dispose()
                val progressBar = callbacks.fetchActivity().getMainProgressBar()
                progressBar.visibility = View.GONE
                callback.onResult(meaningsResponse.results, null, 2)
//                LogUtils.D("JIMX", LogUtils.FilterTags.withTags(LogUtils.TagFilter.API), String.format("$$$$$$$$$$$$$$$$$$$$$$*=%d", meaningsResponse.metaData.total))
            },
            { throwable ->
                disposable?.dispose()
                val progressBar = callbacks.fetchActivity().getMainProgressBar()
                progressBar.visibility = View.GONE
                getUrbanDictionarySchoolsDataFailure(callbacks.fetchActivity(),"getUrbanDictionarySchoolsData", throwable)
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SchoolField?>) { }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SchoolField?>) {
        var disposable: Disposable? = null
        disposable = apiInterface.getSchoolsData(params.key, Constants.NEWSFEED_API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .timeout(15L, TimeUnit.SECONDS)
            .subscribe({ meaningsResponse ->
                disposable?.dispose()
                val progressBar = callbacks.fetchActivity().getMainProgressBar()
                progressBar.visibility = View.GONE
                //val nextKey: Int? = if (params.key == meaningsResponse.metaData.total) null else params.key + 1
                callback.onResult(meaningsResponse.results, params.key + 1)
            },
            { throwable ->
                disposable?.dispose()
                val progressBar = callbacks.fetchActivity().getMainProgressBar()
                progressBar.visibility = View.GONE
                getUrbanDictionarySchoolsDataFailure(callbacks.fetchActivity(),"getUrbanDictionarySchoolsData", throwable)
            })
    }

    private fun getUrbanDictionarySchoolsDataFailure(activity: MainActivity, method: String, throwable: Throwable) {
        LogUtils.E(LogUtils.FilterTags.withTags(LogUtils.TagFilter.EXC), method, throwable)
        StandardModal(
            activity,
            modalType = StandardModal.ModalType.STANDARD,
            titleText = activity.getString(R.string.std_modal_urban_dictionary_failure_title),
            bodyText = activity.getString(R.string.std_modal_urban_dictionary_failure_body),
            positiveText = activity.getString(R.string.std_modal_ok),
            dialogFinishedListener = object : StandardModal.DialogFinishedListener {
                override fun onPositive(string: String) { }
                override fun onNegative() { }
                override fun onNeutral() { }
                override fun onBackPressed() { }
            }
        ).show(activity.supportFragmentManager, "MODAL")
    }

}