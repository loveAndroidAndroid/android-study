package com.moudle.xiecheng

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.moudle.xiecheng.ui.BlockingActivity
import com.moudle.xiecheng.ui.KotlinXActivity
import com.moudle.xiecheng.ui.SuspendActivity
import com.moudle.xiecheng.ui.WhatActivity

/**
 * 协程与语言无关，任务执行的一个概念，可以挂机和恢复
 * 回掉不恶心，早就习惯到骨头里去了
 * 解决回调问题的一个工具
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val What = findViewById<View>(R.id.What) as TextView
        val suspend = findViewById<View>(R.id.suspend) as TextView
        val allow = findViewById<View>(R.id.allow) as TextView
        val kotlinx = findViewById<View>(R.id.kotlinx) as TextView

        What.setOnClickListener(this)
        suspend.setOnClickListener(this)
        allow.setOnClickListener(this)
        kotlinx.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.What -> {
                val intent = Intent(this@MainActivity, WhatActivity::class.java)
                startActivity(intent)
            }
            R.id.suspend -> {
                val intent = Intent(this@MainActivity, SuspendActivity::class.java)
                startActivity(intent)
            }
            R.id.allow -> {
                val intent = Intent(this@MainActivity, BlockingActivity::class.java)
                startActivity(intent)
            }
            R.id.kotlinx -> {
                val intent = Intent(this@MainActivity, KotlinXActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
