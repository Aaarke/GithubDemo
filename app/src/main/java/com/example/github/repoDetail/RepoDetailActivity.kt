package com.example.github.repoDetail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.github.R
import com.example.github.contriButor.ContriButorActivity
import com.example.github.home.OnRepoItemClickedListener
import com.example.github.model.Item
import com.example.github.utility.Keys
import com.example.github.webView.WebViewActivity
import com.google.android.material.appbar.AppBarLayout
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

    }

    override fun onStart() {
        super.onStart()
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
            override fun onItemClicked(pos:Int,item: Item,imageView: ImageView) {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransitionName =
                extras?.getString(Keys.EXTRAS.EXTRA_AVTAR_IMAGE_TRANSITION_NAME)
            ivAuthor.transitionName = imageTransitionName
        }
        tvNameValue.text = item?.name
        tvLinkValue.text = item?.cloneUrl
        tvRepoDescriptionValue.text = item?.description
        Glide.with(this)
            .asBitmap()
            .load(item?.owner?.avatarUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    ivAuthor.setImageBitmap(resource)
                    setToolbarColor(resource)
                }
            })

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
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        tvLinkValue.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(Keys.EXTRAS.URL, item?.htmlUrl)
            startActivity(intent)
        }

    }



    fun Lightness(color: Int): Float {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        val hsl = FloatArray(3)
        ColorUtils.RGBToHSL(red, green, blue, hsl)
        return hsl[2]


    }

    // Set the background and text colors of a toolbar given a
// bitmap image to match
    fun setToolbarColor(bitmap: Bitmap) {
        // Generate the palette and get the vibrant swatch
        val vibrantSwatch = createPaletteSync(bitmap).vibrantSwatch

        // Set the toolbar background and text colors.
        // Fall back to default colors if the vibrant swatch is not available.
        with(findViewById<AppBarLayout>(R.id.app_bar)) {
            setBackgroundColor(vibrantSwatch?.rgb ?:
            ContextCompat.getColor(context, R.color.material_grey300))
        }
    }



    // Generate palette synchronously and return it
    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()







}
