package com.example.github.repoDetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.github.R
import com.example.github.contriButor.ContriButorActivity
import com.example.github.home.OnRepoItemClickedListener
import com.example.github.model.Item
import com.example.github.utility.Keys
import com.example.github.webView.WebViewActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_repo_detail.*
import kotlinx.android.synthetic.main.content_repo_detail.*


class RepoDetailActivity : AppCompatActivity() {
    private lateinit var repoDetailViewModel: RepoDetailViewModel
    private var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
        repoDetailViewModel = ViewModelProviders.of(this).get(RepoDetailViewModel::class.java)
        setSupportActionBar(toolbar)
        setInitialUI()
        setValueInUi()
        getListOfContriButer()

    }

    private fun getListOfContriButer() {
        repoDetailViewModel.getAllContriButerList(item?.contributorsUrl!!)
        repoDetailViewModel.contriButerListItem.observe(this, Observer {
            setAdapter(it)
        })

        repoDetailViewModel.loadingVisibility.observe(this, Observer {
            if (it == View.GONE) {
                pbContriButorLoader.visibility = View.GONE
            } else {
                pbContriButorLoader.visibility = View.VISIBLE

            }
        })
    }

    private fun setAdapter(items: ArrayList<Item>?) {
        val mContributorAdapter = ContributorAdapter(this, items,object:
            OnRepoItemClickedListener {
            override fun onItemClicked(item: Item) {
                val bundle = Bundle()
                val i = Intent(this@RepoDetailActivity, ContriButorActivity::class.java)
                bundle.putSerializable(Keys.EXTRAS.REPO_ITEM, item)
                i.putExtras(bundle)
                startActivity(i)
            }
        })
        val mGridLayoutManager = GridLayoutManager(this,4)
        mGridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                mGridLayoutManager.spanCount
                return 1
            }
        }
        rvContriButor.layoutManager = mGridLayoutManager
        rvContriButor.adapter = mContributorAdapter
    }

    private fun setValueInUi() {
        val extras = intent.extras

        item = if (extras != null) {
            extras.getSerializable(Keys.EXTRAS.REPO_ITEM) as Item?
        } else {
            null
        }
        tvNameValue.text = item?.name
        tvLinkValue.text = item?.cloneUrl
        tvRepoDescriptionValue.text = item?.description
        Glide.with(this)
            .load(item?.owner?.avatarUrl)
            .into(ivAuthor)

    }

    private fun setInitialUI() {
        val collapsingToolbar = findViewById<View>(R.id.toolbar_layout) as CollapsingToolbarLayout
        collapsingToolbar.title = getString(R.string.repo_detail)
        collapsingToolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(
                this,
                R.color.material_black
            )
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        Glide.with(this)
            .load(R.drawable.default_image)
            .apply(RequestOptions.circleCropTransform())
            .into(ivAuthor)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        tvLinkValue.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(Keys.EXTRAS.URL, item?.htmlUrl)
            startActivity(intent)
        }
    }


}
