package com.example.github.repoDetail

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.example.github.R
import com.example.github.home.OnRepoItemClickedListener
import com.example.github.model.Item
import kotlinx.android.synthetic.main.item_contributor.view.*

class ContributorAdapter(
    var context: Context, var list: ArrayList<Item>?,
    var onRepoItemClickedListener: OnRepoItemClickedListener
) :
    RecyclerView.Adapter<ContributorAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contributor, parent, false)
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
                .load(list?.get(position)?.avtarUrl)
                .into(object : BitmapImageViewTarget(holder.itemView.ivContriButor) {
                    override fun setResource(resource: Bitmap?) {
                        holder.itemView.ivContriButor.setImageBitmap(resource)
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.itemView.tvContriButorName.text = list?.get(position)?.login
        holder.itemView.setOnClickListener {
            onRepoItemClickedListener.onItemClicked(position,list?.get(position)!!,holder.itemView.ivContriButor)
        }
    }


    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}