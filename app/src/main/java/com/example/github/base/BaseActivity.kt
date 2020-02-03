package com.example.github.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.github.R
import com.example.github.utility.Utils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_base.*


open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.actionbar)

        if (Utils.internetCheck(this)) {
            ivNoInternet.visibility = View.GONE
        } else {
            val snackbar = Snackbar
                .make(ivNoInternet, "No internet connection", Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }
}
