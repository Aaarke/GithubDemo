package com.example.github.repoDetail

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.github.R
import com.example.github.base.BaseViewModel
import com.example.github.model.Item
import com.example.github.network.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepoDetailViewModel : BaseViewModel() {
    var apiInterface: ApiInterface? = null
        @Inject set
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    private val subscription = CompositeDisposable()
    var contriButerListItem = MutableLiveData<ArrayList<Item>>()


    fun getAllContriButerList(url: String) {
        subscription.add(
            apiInterface?.getContriButers(url)!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveContributerListStart() }
                .doOnTerminate { onRetrieveContributerListFinish() }
                .subscribe(
                    { result -> onRetrieveContributerListSuccess(result) },
                    { onRetrieveContributerListError() }
                )
        )
    }

    private fun onRetrieveContributerListError() {
        errorMessage.value = R.string.post_error

    }

    private fun onRetrieveContributerListSuccess(result: ArrayList<Item>) {
        contriButerListItem.value = result

    }

    private fun onRetrieveContributerListFinish() {
        loadingVisibility.value = View.GONE

    }

    private fun onRetrieveContributerListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

}