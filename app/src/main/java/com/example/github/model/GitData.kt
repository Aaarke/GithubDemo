package com.example.github.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class GitData(
    @SerializedName("total_count")
    @Expose
    val totalCount: Int? = null,
    @SerializedName("incomplete_results")
    @Expose
    val incompleteResults: Boolean? = null,
    @SerializedName("items")
    @Expose
    val items: ArrayList<Item>? = null

):Serializable