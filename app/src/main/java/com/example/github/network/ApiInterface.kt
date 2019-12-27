package com.example.github.network

import com.example.github.model.GitData
import com.example.github.model.Item
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface ApiInterface {
    /**
     * Get the list of the pots from the API
     */
    @GET("/search/repositories")
    fun searchGitRepo(@QueryMap map: HashMap<String, String>?): Observable<GitData>

    @GET
    fun getContriButers(@Url contriUrl:String):Observable<ArrayList<Item>>

    @GET
    fun getAllRepoOfUser(@Url reposUrl:String)
}
