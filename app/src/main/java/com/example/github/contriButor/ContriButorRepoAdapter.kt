package com.example.github.contriButor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github.R
import com.example.github.model.Item
import kotlinx.android.synthetic.main.item_repo_list.view.*

class ContriButorRepoAdapter(var context: Context, var listOfItem: ArrayList<Item>?) :
    RecyclerView.Adapter<ContriButorRepoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listOfItem?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvRepoName.text = listOfItem?.get(position)?.name
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}