package com.fullsekurity.deptofeducation.schools

import android.view.View
import androidx.paging.PageKeyedDataSource
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
    private var totalRecords: Int = 0

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, SchoolField?>) {
        callbacks.fetchActivity().runOnUiThread {
            val progressBar = callbacks.fetchActivity().getMainProgressBar()
            progressBar.visibility = View.VISIBLE
        }
        var disposable: Disposable? = null
        disposable = apiInterface.getSchoolsData(1, Constants.EDUC_DEPT_API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .timeout(60L, TimeUnit.SECONDS)
            .subscribe({ meaningsResponse ->
                disposable?.dispose()
                val progressBar = callbacks.fetchActivity().getMainProgressBar()
                progressBar.visibility = View.GONE
                callback.onResult(meaningsResponse.results, null, 2)
                totalRecords = meaningsResponse.metaData.total
            },
            { throwable ->
                disposable?.dispose()
                val progressBar = callbacks.fetchActivity().getMainProgressBar()
                progressBar.visibility = View.GONE
                getSchoolsDataFailure(callbacks.fetchActivity(),"getSchoolsData", throwable)
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SchoolField?>) { }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SchoolField?>) {
        callbacks.fetchActivity().runOnUiThread {
            val progressBar = callbacks.fetchActivity().getMainProgressBar()
            progressBar.visibility = View.VISIBLE
        }
        var disposable: Disposable? = null
        disposable = apiInterface.getSchoolsData(params.key, Constants.EDUC_DEPT_API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .timeout(60L, TimeUnit.SECONDS)
            .subscribe({ meaningsResponse ->
                disposable?.dispose()
                val progressBar = callbacks.fetchActivity().getMainProgressBar()
                progressBar.visibility = View.GONE
                val nextKey: Int? = if (params.key == totalRecords) null else params.key + 1
                callback.onResult(meaningsResponse.results, nextKey)
            },
            { throwable ->
                disposable?.dispose()
                val progressBar = callbacks.fetchActivity().getMainProgressBar()
                progressBar.visibility = View.GONE
                getSchoolsDataFailure(callbacks.fetchActivity(),"getSchoolsData", throwable)
            })
    }

    private fun getSchoolsDataFailure(activity: MainActivity, method: String, throwable: Throwable) {
        LogUtils.E(LogUtils.FilterTags.withTags(LogUtils.TagFilter.EXC), method, throwable)
        StandardModal(
            activity,
            modalType = StandardModal.ModalType.STANDARD,
            titleText = activity.getString(R.string.std_modal_dept_of_education_failure_title),
            bodyText = activity.getString(R.string.std_modal_dept_of_education_failure_body),
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