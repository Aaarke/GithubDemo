package com.example.github.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.R
import com.example.github.home.GitSearchAdapter
import com.example.github.home.HomeViewModel
import com.example.github.home.OnRepoItemClickedListener
import com.example.github.model.Item
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {
    private var listener: OnHomeFragmentInteractionListener? = null
    private var mGitSearchAdapter: GitSearchAdapter? = null

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.getGitData("tetris")
        homeViewModel.gitData.observe(this, Observer {
            setAdapter(it.items)
        })

        homeViewModel.loadingVisibility.observe(this, Observer {
            if (it == View.GONE) {
                pbHomeLoader.visibility = View.GONE
                main.isRefreshing = false
            } else {
                pbHomeLoader.visibility = View.VISIBLE
            }
        })

        main.setOnRefreshListener {
            main.isRefreshing = false
            homeViewModel.getGitData("tetris")
        }


    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHomeFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    interface OnHomeFragmentInteractionListener {
        fun onFragmentInteraction(item: Item)
    }

    private fun setAdapter(items: ArrayList<Item>?) {
        val sortedList: ArrayList<Item> = ArrayList()
        sortedList.addAll(items?.sortedWith(compareBy { it.watchersCount })?.reversed()!!)
        mGitSearchAdapter =
            GitSearchAdapter(context!!, sortedList, object : OnRepoItemClickedListener {
                override fun onItemClicked(item: Item) {
                    listener?.onFragmentInteraction(item)
                }
            })
        val mLinearLayoutManager =
            LinearLayoutManager(activity)
        rvGitRepo.layoutManager = mLinearLayoutManager
        rvGitRepo.adapter = mGitSearchAdapter
    }

    fun searchRepo(searchString: String) {
        homeViewModel.getGitData(searchString)
    }


}
