package com.example.github.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.R
import com.example.github.databinding.HomeFragmentBinding
import com.example.github.home.*
import com.example.github.model.Item
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {
    private var listener: OnHomeFragmentInteractionListener? = null
    private var mGitSearchAdapter: GitSearchAdapter? = null

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: HomeFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.home_fragment, container, false
        )
        return binding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = HomeViewModelFactory()
        homeViewModel =ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        homeViewModel.filterTextAll.value = "tetris"
        setObserver()
        binding.main.setOnRefreshListener {
            main.isRefreshing = false

        }
    }

    private fun setObserver() {
        homeViewModel.pagedListLiveData?.observe(viewLifecycleOwner, Observer {
            setAdapter(it)
        })


        homeViewModel.viewStateLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
//                is IViewModel.ViewState.LoadingState -> {
//                    if (rvGitRepo.visibility == View.GONE) {
//                        rvGitRepo.visibility = View.VISIBLE
//                    }
//                    pbHomeLoader.visibility = View.VISIBLE
//                    tvError.visibility = View.GONE
//                }
//                is IViewModel.ViewState.EmptyState -> {
//                    pbHomeLoader.visibility = View.GONE
//                    rvGitRepo.visibility = View.GONE
//                    tvError.visibility = View.VISIBLE
//                    tvError.text = getString(R.string.no_result)
//                }
//                is IViewModel.ViewState.ErrorState -> {
//                    pbHomeLoader.visibility = View.GONE
//                }

                is IViewModel.ViewState.successState -> {
                    startAnimation()
                }


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
        fun onFragmentInteraction(position: Int, item: Item, imageView: ImageView)
    }

    private fun setAdapter(items: PagedList<Item>) {
        val sortedList: ArrayList<Item> = ArrayList()
        sortedList.addAll(items.sortedWith(compareBy { it.watchersCount }).reversed())
        mGitSearchAdapter =
            GitSearchAdapter(context!!, object : OnRepoItemClickedListener {
                override fun onItemClicked(position: Int, item: Item, imageView: ImageView) {
                    listener?.onFragmentInteraction(position, item, imageView)
                }
            })
        mGitSearchAdapter!!.submitList(items)
        binding.myAdapter=mGitSearchAdapter

//        val mLinearLayoutManager =
//            LinearLayoutManager(activity)
//        rvGitRepo.layoutManager = mLinearLayoutManager
//        rvGitRepo.adapter = mGitSearchAdapter
        startAnimation()

    }

    private fun startAnimation() {
        val animation =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        rvGitRepo.layoutAnimation = animation
    }

    fun searchRepo(searchString: String) {
        homeViewModel.filterTextAll.value = searchString
    }


}
