package com.example.github.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.example.github.R
import com.example.github.home.fragment.HomeFragment
import com.example.github.model.Item
import com.example.github.repoDetail.RepoDetailActivity
import com.example.github.utility.Keys


class HomeActivity : AppCompatActivity(), HomeFragment.OnHomeFragmentInteractionListener {
    override fun onFragmentInteraction(item: Item) {
        val bundle = Bundle()
        val i = Intent(this, RepoDetailActivity::class.java)
        bundle.putSerializable(Keys.EXTRAS.REPO_ITEM, item)
        i.putExtras(bundle)
        startActivity(i)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView? = searchItem?.actionView as SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return super.onCreateOptionsMenu(menu)
    }


}
