package com.example.github.home

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.example.github.R
import com.example.github.model.Item
import kotlinx.android.synthetic.main.item_all_git.view.*

class GitSearchAdapter(
    var context: Context,
    var list: ArrayList<Item>?,
    var onRepoItemClickedListener: OnRepoItemClickedListener
) :
    RecyclerView.Adapter<GitSearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_all_git, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            Glide.with(context)
                .asBitmap()
                .apply(
                    RequestOptions().fitCenter().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .placeholder(R.drawable.ic_placeholder).circleCrop()
                )
                .load(list?.get(position)?.owner?.avatarUrl)
                .into(object : BitmapImageViewTarget(holder.itemView.ivAvtar) {
                    override fun setResource(resource: Bitmap?) {
                        holder.itemView.ivAvtar.setImageBitmap(resource)
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        ViewCompat.setTransitionName(holder.itemView.ivAvtar,list?.get(position)?.htmlUrl)

        holder.itemView.setOnClickListener {
            onRepoItemClickedListener.onItemClicked(position,list?.get(position)!!,holder.itemView.ivAvtar)
        }
        holder.itemView.let {
            it.tvName.text = list?.get(position)?.name
            it.tvFullName.text = list?.get(position)?.fullName
            it.tvWatcherCount.text = list?.get(position)?.watchersCount.toString()
            it.tvStarGazzers.text = list?.get(position)?.stargazersCount.toString()
            it.tvTotalFork.text = list?.get(position)?.forksCount.toString()
        }
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}