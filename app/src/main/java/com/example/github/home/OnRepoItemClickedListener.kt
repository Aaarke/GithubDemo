package com.example.github.home

import android.widget.ImageView
import com.example.github.model.Item

interface OnRepoItemClickedListener {
    fun onItemClicked(position:Int,item: Item,imageView: ImageView)
}