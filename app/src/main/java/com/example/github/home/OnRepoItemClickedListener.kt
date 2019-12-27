package com.example.github.home

import com.example.github.model.Item

interface OnRepoItemClickedListener {
    fun onItemClicked(item: Item)
}