package com.example.github.model

import android.content.ClipData.Item


data class GitData(
    val totalCount: Int? = null,
    val incompleteResults: Boolean? = null,
    val items: List<Item>? = null

)