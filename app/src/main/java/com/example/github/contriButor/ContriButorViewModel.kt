package com.example.github.contriButor

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.github.R
import com.example.github.base.BaseViewModel
import com.example.github.model.GitData
import com.example.github.model.Item
import com.example.github.network.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContriButorViewModel : BaseViewModel() {
    var apiInterface: ApiInterface? = null
        @Inject set
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    private val subscription = CompositeDisposable()
    var listOfRepo = MutableLiveData<ArrayList<Item>>()


    fun getListOfRepos(url: String) {
        subscription.add(
            apiInterface?.getAllRepoOfUser(url)!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveContributerRepoListStart() }
                .doOnTerminate { onRetrieveContributerRepoListFinish() }
                .subscribe(
                    { result -> onRetrieveContributerRepoListSuccess(result) },
                    { onRetrieveContributerRepoListError() }
                )
        )
    }

    private fun onRetrieveContributerRepoListError() {
        errorMessage.value = R.string.post_error

    }

    private fun onRetrieveContributerRepoListSuccess(result: ArrayList<Item>) {
        listOfRepo.value = result
    }

    private fun onRetrieveContributerRepoListFinish() {
        loadingVisibility.value = View.GONE

    }

    private fun onRetrieveContributerRepoListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }
}