package com.example.github.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.example.github.R
import com.example.github.base.BaseActivity
import com.example.github.home.fragment.HomeFragment
import com.example.github.model.Item
import com.example.github.repoDetail.RepoDetailActivity
import com.example.github.utility.Constants
import com.example.github.utility.Keys
import com.example.github.utility.Keys.EXTRAS.Companion.EXTRA_AVTAR_IMAGE_TRANSITION_NAME


class HomeActivity : BaseActivity(), HomeFragment.OnHomeFragmentInteractionListener {
    override fun onFragmentInteraction(position: Int, item: Item, sharedImageView: ImageView) {
        val bundle = Bundle()
        val i = Intent(this, RepoDetailActivity::class.java)
        bundle.putSerializable(Keys.EXTRAS.REPO_ITEM, item)
        i.putExtras(bundle)
        i.putExtra(EXTRA_AVTAR_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImageView))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            sharedImageView,
            ViewCompat.getTransitionName(sharedImageView)!!
        )
        startActivity(i, options.toBundle())
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
        val searchView = myActionMenuItem?.actionView as SearchView
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
