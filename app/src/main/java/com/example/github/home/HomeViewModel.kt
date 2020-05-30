package com.example.github.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.github.base.BaseViewModel
import com.example.github.model.Item
import com.example.github.network.ApiInterface
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject


class HomeViewModel : BaseViewModel() ,IViewModel{


    var apiInterface: ApiInterface? = null
        @Inject set
    var errorMessage: MutableLiveData<String>? = MutableLiveData()
    var executor: Executor? = null
    var pagedListLiveData: LiveData<PagedList<Item>>? = null
    var repoDataSourceFactory: RepoDataSourceFactory
    var filterTextAll = MutableLiveData<String>()
    val viewStateLiveData : MutableLiveData<IViewModel.ViewState> = MutableLiveData()

    init {
        repoDataSourceFactory =
            RepoDataSourceFactory("tetris", apiInterface,this)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .setPrefetchDistance(7)
            .build()
        pagedListLiveData =
            Transformations.switchMap(filterTextAll) { input ->
                repoDataSourceFactory =
                    RepoDataSourceFactory(input, apiInterface, this)
                repoDataSourceFactory.errorMessage
                executor = Executors.newFixedThreadPool(5)
                 LivePagedListBuilder<Int, Item>(repoDataSourceFactory, config)
                    .build()
            }


    }

    override fun setState(state: IViewModel.ViewState) {
        when(state){
            is   IViewModel.ViewState.EmptyState->{
                viewStateLiveData.postValue(IViewModel.ViewState.EmptyState)

            }

            is  IViewModel.ViewState.LoadingState->{
                viewStateLiveData.postValue(IViewModel.ViewState.LoadingState)

            }

            is  IViewModel.ViewState.ErrorState->{
                viewStateLiveData.postValue(IViewModel.ViewState.ErrorState)

            }

            is  IViewModel.ViewState.successState->{
                viewStateLiveData.postValue(IViewModel.ViewState.successState)

            }
        }

    }



}
