package com.example.github.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.example.github.R
import com.example.github.base.BaseActivity
import com.example.github.home.fragment.HomeFragment
import com.example.github.model.Item
import com.example.github.repoDetail.RepoDetailActivity
import com.example.github.utility.Constants
import com.example.github.utility.Keys


class HomeActivity : BaseActivity(), HomeFragment.OnHomeFragmentInteractionListener {
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
                .replace(R.id.container, HomeFragment.newInstance(), Constants.Fragment.FRAG_HOME)
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard, menu);
        val myActionMenuItem = menu?.findItem(R.id.action_search)
        var searchView = myActionMenuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!searchView.isIconified) {
                    searchView.isIconified = true
                }
                myActionMenuItem.collapseActionView()
                val frag =
                    supportFragmentManager.findFragmentByTag(Constants.Fragment.FRAG_HOME) as HomeFragment
                frag.searchRepo(query)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        return true
    }


}
