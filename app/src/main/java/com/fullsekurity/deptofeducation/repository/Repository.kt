package com.fullsekurity.deptofeducation.repository

import com.fullsekurity.deptofeducation.R
import com.fullsekurity.deptofeducation.activity.Callbacks
import com.fullsekurity.deptofeducation.activity.MainActivity
import com.fullsekurity.deptofeducation.logger.LogUtils
import com.fullsekurity.deptofeducation.modal.StandardModal
import com.fullsekurity.deptofeducation.repository.network.APIClient
import com.fullsekurity.deptofeducation.repository.network.APIInterface
import com.fullsekurity.deptofeducation.repository.storage.SchoolField
import com.fullsekurity.deptofeducation.repository.storage.SchoolsData
import com.fullsekurity.deptofeducation.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class Repository(private val callbacks: Callbacks) {

    private val meaningsService: APIInterface = APIClient.client

    fun getUrbanDictionarySchoolsData(country: String, showSchoolsData: (meaningsList: List<SchoolField>?) -> Unit) {
        var disposable: Disposable? = null
        disposable = meaningsService.getSchoolsData(100, Constants.NEWSFEED_API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .timeout(15L, TimeUnit.SECONDS)
            .subscribe ({ meaningsResponse ->
                disposable?.dispose()
                showSchoolsData(meaningsResponse.results)
            },
            { throwable ->
                disposable?.dispose()
                showSchoolsData(null)
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