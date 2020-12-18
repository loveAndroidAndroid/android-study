package com.moudle.xiecheng.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.moudle.xiecheng.MainViewModel
import com.moudle.xiecheng.R
import com.moudle.xiecheng.net.RetrofitManager
import com.moudle.xiecheng.net.dataConvert
import kotlinx.coroutines.*
import java.lang.Runnable
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

class BlockingActivity : AppCompatActivity(), CoroutineScope {

    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var blocking: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blocking)

        job = Job()

        blocking = findViewById<View>(R.id.blocking) as TextView

        blockingCoroutineScope()

    }

    // 非阻塞式是相对阻塞式而言的。
    // 然而阻塞不阻塞，都是针对单线程讲的，一旦切了线程，那就是是非阻塞的.任务去别的线程执行了，之前的线程就自由了，可以继续做别的事情了。
    // 协程的挂起，就是非阻塞式的.因为协程在挂起的同时切线程这件事情。
    // 协程在写法上和单线程的阻塞式是一样的，协程只是在写法上「看起来阻塞」，其实是「非阻塞」的，因为它做了很多工作，其中有一个就是帮我们切线程。
    private fun blockingCoroutineScope() {
        blocking.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                // 在主线程开启协程
                launch(Dispatchers.Main) {
                    //耗时操作和更新 UI 的逻辑像写单线程一样放在了一起，只是在外面包了一层协程。在login里面，Retrofit帮我们做了。
                    //而Retrofit创建的这个协程解决了原来我们单线程写法会卡线程这件事
                    val userInfo = RetrofitManager.service.login("zhang1", "123456").dataConvert()
                    Toast.makeText(this@BlockingActivity, userInfo.username, Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    //协程 挂起的非阻塞式指的是它能用看起来阻塞的代码写出非阻塞的操作，这就是协程的非阻塞式吧。

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
