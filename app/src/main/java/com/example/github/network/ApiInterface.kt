package com.example.github.network

import com.example.github.model.GitData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {
    /**
     * Get the list of the pots from the API
     */
    @GET("/search/repositories")
    fun searchGitRepo(@QueryMap map: HashMap<String, String>?): Observable<GitData>
}
