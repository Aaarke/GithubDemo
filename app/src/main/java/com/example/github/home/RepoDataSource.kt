package com.example.github.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.github.model.Item
import com.example.github.network.ApiInterface
import com.example.github.utility.Keys
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class RepoDataSource(
    private val searchString: String,
    var apiInterface: ApiInterface?,
    var homeViewModel: IViewModel
) : PageKeyedDataSource<Int, Item>() {

    var isLoading:MutableLiveData<Boolean> = MutableLiveData()
    @SuppressLint("CheckResult")

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Item>
    ) {
        val map = HashMap<String, String>()
        map[Keys.ApiField.REQ_Q] = searchString
        map[Keys.ApiField.REQ_SORT] = "stars"
        map[Keys.ApiField.REQ_ORDER] = "desc"
        map[Keys.ApiField.REQ_PAGE] = "1"

        apiInterface?.searchGitRepo(map)!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                homeViewModel.setState(IViewModel.ViewState.LoadingState)
            }

            .subscribe(
                { result ->
                    if(result.items.isNullOrEmpty()){
                        homeViewModel.setState(IViewModel.ViewState.EmptyState)
                    }else{
                        homeViewModel.setState(IViewModel.ViewState.successState)
                        callback.onResult(result.items, null, 2)
                    }
                },
                {
                    homeViewModel.setState(IViewModel.ViewState.ErrorState)
                }
            )
    }


    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        if (params.key != null) {
            val map = HashMap<String, String>()
            map[Keys.ApiField.REQ_Q] = searchString
            map[Keys.ApiField.REQ_SORT] = "stars"
            map[Keys.ApiField.REQ_ORDER] = "desc"
            map[Keys.ApiField.REQ_PAGE] = (params.key + 1).toString()
            apiInterface?.searchGitRepo(map)!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                  homeViewModel.setState(IViewModel.ViewState.LoadingState)
                }
                .subscribe(
                    { result ->
                        if(result.items.isNullOrEmpty()){
                            homeViewModel.setState(IViewModel.ViewState.EmptyState)
                        }else{
                            homeViewModel.setState(IViewModel.ViewState.successState)
                            callback.onResult(result.items!!, params.key + 1)
                        }

                    },
                    {
                        homeViewModel.setState(IViewModel.ViewState.ErrorState)
                    }
                )
        }


    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
    }




}