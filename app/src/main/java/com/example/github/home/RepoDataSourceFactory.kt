package com.example.github.home

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.github.model.Item
import com.example.github.network.ApiInterface


class RepoDataSourceFactory(
    private val searchString: String,
    var apiInterface: ApiInterface?,
    var homeViewModel: IViewModel
) : DataSource.Factory<Int, Item>() {
    var mutableLiveData: MutableLiveData<RepoDataSource>? = MutableLiveData()
    var errorMessage: MutableLiveData<String>? = MutableLiveData()


    override fun create(): DataSource<Int, Item> {
        val repoDataSource = RepoDataSource(searchString, apiInterface,homeViewModel)
        mutableLiveData?.postValue(repoDataSource)
        return repoDataSource
    }

}