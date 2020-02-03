package com.example.github.home

interface IViewModel {
    fun setState(state: ViewState)


    sealed class ViewState {
        object  ErrorState : ViewState()
        object EmptyState : ViewState()
        object LoadingState:ViewState()
        object successState:ViewState()
    }
}