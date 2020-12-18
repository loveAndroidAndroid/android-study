package com.moudle.xiecheng.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.moudle.xiecheng.MainViewModel
import com.moudle.xiecheng.R

class KotlinXActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kot)

        val regiest = findViewById<View>(R.id.regiest) as TextView
        val login = findViewById<View>(R.id.login) as TextView
        val banner = findViewById<View>(R.id.banner) as TextView

        regiest.setOnClickListener(this)
        login.setOnClickListener(this)
        banner.setOnClickListener(this)

        /*创建viewmodel*/
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        startObserve()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.regiest -> {
                mainViewModel.register()
            }
            R.id.login -> {
                mainViewModel.login()
            }
            R.id.banner -> {
                mainViewModel.getbanner()
            }
        }
    }

    fun startObserve() {
        mainViewModel.apply {
            userInfoRegister.observe(this@KotlinXActivity, Observer { it ->
                Log.i("请求成功", userInfoRegister.value.toString())
                Toast.makeText(this@KotlinXActivity, userInfoRegister.value?.username, Toast.LENGTH_LONG)
                    .show()
            })

            userInfoLogin.observe(this@KotlinXActivity, Observer { it ->
                Log.i("请求成功", userInfoLogin.value.toString())
                Toast.makeText(this@KotlinXActivity, userInfoLogin.value?.username, Toast.LENGTH_LONG)
                    .show()
            })
        }
    }
}
