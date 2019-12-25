package com.example.github.home

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.github.R
import com.example.github.base.BaseViewModel
import com.example.github.model.GitData
import com.example.github.network.ApiInterface
import com.example.github.utility.Keys
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {
    var apiInterface: ApiInterface? = null
        @Inject set
    var gitData = MutableLiveData<GitData>()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()

    private val subscription = CompositeDisposable()


    fun getGitData(){
        val map = HashMap<String, String>()
        map[Keys.ApiField.REQ_Q]="tetris"
        map[Keys.ApiField.REQ_SORT] = "stars"
        map[Keys.ApiField.REQ_ORDER] = "desc"
        subscription.add(
            apiInterface?.searchGitRepo(map)!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveSearchGitRepoStart() }
                .doOnTerminate { onRetrieveSearchGitRepoFinish() }
                .subscribe(
                    { result -> onRetrieveSearchRepoSuccess(result) },
                    { onRetrieveSearchGitRepoError() }
                )
        )

    }

    private fun onRetrieveSearchGitRepoFinish() {
        loadingVisibility.value = View.GONE

    }

    private fun onRetrieveSearchGitRepoStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null

    }

    private fun onRetrieveSearchGitRepoError() {
        errorMessage.value = R.string.post_error

    }

    private fun onRetrieveSearchRepoSuccess(result: GitData?) {
        gitData.value=result
    }


}
