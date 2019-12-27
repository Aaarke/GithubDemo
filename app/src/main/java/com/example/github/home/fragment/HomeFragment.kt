package com.example.github.home.fragment

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.R
import com.example.github.home.GitSearchAdapter
import com.example.github.home.HomeViewModel
import com.example.github.home.OnRepoItemClickedListener
import com.example.github.model.Item
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {
    private var listener: OnHomeFragmentInteractionListener? = null

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
        homeViewModel.getGitData()
        homeViewModel.gitData.observe(this, Observer {
            setAdapter(it.items)
        })

        homeViewModel.loadingVisibility.observe(this, Observer {
            if (it == View.GONE) {
                pbHomeLoader.visibility = View.GONE
            } else {
                pbHomeLoader.visibility = View.VISIBLE
            }
        })


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
        val mGitSearchAdapter = GitSearchAdapter(context!!, items,object:OnRepoItemClickedListener{
            override fun onItemClicked(item: Item) {
                listener?.onFragmentInteraction(item)
            }
        })
        val mLinearLayoutManager =
            LinearLayoutManager(activity)
        rvGitRepo.layoutManager = mLinearLayoutManager
        rvGitRepo.adapter = mGitSearchAdapter
    }


}
