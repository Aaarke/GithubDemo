package com.example.github.contriButor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.github.R
import com.example.github.model.Item
import com.example.github.utility.Keys
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_contri_butor.*
import kotlinx.android.synthetic.main.activity_contri_butor.toolbar
import kotlinx.android.synthetic.main.activity_repo_detail.*
import kotlinx.android.synthetic.main.content_contri_butor.*

class ContriButorActivity : AppCompatActivity() {
    private lateinit var contriButorViewModel: ContriButorViewModel
    private var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contri_butor)
        setSupportActionBar(toolbar)
        contriButorViewModel = ViewModelProviders.of(this).get(ContriButorViewModel::class.java)
        setInitUi()
        setListOfRepo()

    }

    private fun setListOfRepo() {
        contriButorViewModel.getListOfRepos(item?.repoUrl!!)
        contriButorViewModel.listOfRepo.observe(this, Observer {
            setAdapter(it)
        })
        contriButorViewModel.loadingVisibility.observe(this, Observer {
            if (it == View.GONE) {
                pbRepoList.visibility = View.GONE
            } else {
                pbRepoList.visibility = View.VISIBLE
            }
        })
    }

    private fun setAdapter(list: ArrayList<Item>) {
        val mContriButorRepoAdapter = ContriButorRepoAdapter(this, list)
        val mLinearLayoutManager =
            LinearLayoutManager(this)
        rvRepoList.layoutManager = mLinearLayoutManager
        rvRepoList.adapter = mContriButorRepoAdapter
    }

    private fun setInitUi() {
        val collapsingToolbar =
            findViewById<View>(R.id.toolbar_layout_contri) as CollapsingToolbarLayout
        collapsingToolbar.title = getString(R.string.repo_list)
        collapsingToolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(
                this,
                R.color.material_black
            )
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val extras = intent.extras
        item = if (extras != null) {
            extras.getSerializable(Keys.EXTRAS.REPO_ITEM) as Item?
        } else {
            null
        }
        Glide.with(this)
            .load(item?.avtarUrl)
            .into(ivContriButor)

    }
}
