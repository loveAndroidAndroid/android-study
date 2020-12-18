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

class WhatActivity : AppCompatActivity(), CoroutineScope {

    //CoroutineContext.Element
    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var launch: TextView
    private lateinit var change: TextView
    private lateinit var getdata: TextView
    private lateinit var getdataMore: TextView
    private lateinit var getdataMoreAsync: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_what)

        job = Job()

        launch = findViewById<View>(R.id.launch) as TextView
        change = findViewById<View>(R.id.change) as TextView
        getdata = findViewById<View>(R.id.getdata) as TextView
        getdataMore = findViewById<View>(R.id.getdataMore) as TextView
        getdataMoreAsync = findViewById<View>(R.id.getdataMoreAsync) as TextView

        //启动一个协程
        launchCoroutineScope()

        //协程的线程切换以及写法的注意
        changeCoroutineScope()

        //协程的网络请求(单一)
        getCoroutineScopeDataForNet()

        //协程的网络请求(嵌套)
        getCoroutineScopeDataForNetMore()

        //协程的网络请求(嵌套Async)
        getCoroutineScopeDataForNetMoreAsync()
    }

    /*
    * 创建一个 Thread 的完整写法
    * Thread(object : Runnable {
    *   override fun run() {
    *         ...
    *     }
    *   })
    *
    * // 使用闭包，再简化为
    * Thread {
    *     ...
    *  }
    * */
    private fun launchCoroutineScope() {
        launch.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                // 方法一，使用 runBlocking 顶层函数  单元测试使用
                runBlocking {
                }

                // 方法二，使用 GlobalScope 单例对象
                // 可以直接调用 launch 开启协程
                // 创建的协程需要手动管理
                // GlobalScope.cancel()
                GlobalScope.launch {
                }

                // 方法三，自行通过 CoroutineContext（协程上下文） 创建一个 CoroutineScope 对象
                // 需要一个类型为 CoroutineContext 的参数
                // 优势 可以自己管理线程
                //CoroutineScope可以理解为协程的作用域，可以管理其域内的所有协程。一个CoroutineScope可以有许多的子scope。
                launch {
                }
            }
        })
    }


//    协程的线程切换以及写法的注意
//    Dispatchers.Main：Android 中的主线程
//    Dispatchers.IO：针对磁盘和网络 IO 进行了优化，适合 IO 密集型的任务，比如：读写文件，操作数据库以及网络请求
//    Dispatchers.Default：适合 CPU 密集型的任务，比如计算

//    第一种写法
//    coroutineScope.launch(Dispachers.IO) {
//        ...
//        withContext(Dispachers.Main){
//            ...
//            withContext(Dispachers.IO) {
//                ...
//                withContext(Dispacher.Main) {
//                    ...
//                }
//            }
//        }
//    }

    // 通过第二种写法来实现相同的逻辑  自动切回主线程
//    coroutineScope.launch(Dispachers.Main) {
//        ...
//        withContext(Dispachers.IO) {
//            ...
//        }
//        ...
//        withContext(Dispachers.IO) {
//            ...
//        }
//        ...
//    }

    private fun changeCoroutineScope() {
        change.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                launch(Dispatchers.IO) {
                    //在 后台 线程开始
                    val message = getMessage()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@WhatActivity, message, Toast.LENGTH_LONG).show()
                    }

                }

                launch(Dispatchers.Main) {
                    // 在 UI 线程开始
                    //这个函数可以切换到指定的线程
                    val message = withContext(Dispatchers.IO) {
                        //切换到 IO 线程，并在执行完成后切回 UI 线程
                        val currentThread = Thread.currentThread()
                        "我在" + currentThread.toString() + "返回的消息"
//                        getMessage()
                    }
                    Toast.makeText(this@WhatActivity, message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun getMessage(): String {
        return "我是在子线程中返回的消息"
    }

    //    RetrofitManager.service.login("zhang", "123456",new Callback<UserInfo>() {
    //    @Override
    //    public void success(UserInfo user) {
    //        runOnUiThread(new Runnable() {
    //            @Override
    //            public void run() {
    //            }
    //        })
    //    }
    //
    //    @Override
    //    public void failure(Exception e) {
    //    }
    //});
    private fun getCoroutineScopeDataForNet() {
        getdata.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                // 在主线程开启协程
                launch(Dispatchers.Main) {
                    // IO 线程执行网络请求 等于内部retrofit进行了切换
                    val userInfo = RetrofitManager.service.login("zhang1", "123456").dataConvert()
                    Toast.makeText(this@WhatActivity, userInfo.username, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    //    api.register(...) { userInfo ->
//        api.login(...) { userInfo ->
//            ...merge(userInfo, userInfo)
//        }
//    }
    //嵌套网络请求
    private fun getCoroutineScopeDataForNetMore() {
        getdataMore.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                // 在主线程开启协程
                launch(Dispatchers.Main) {
                    // IO 线程执行网络请求 等于内部retrofit进行了切换
                    val start = System.currentTimeMillis()
                    val userInfoRegister =
                        RetrofitManager.service.register("wenwen2", "123456", "123456")
                            .dataConvert()
                    val userInfoLogin =
                        RetrofitManager.service.login(userInfoRegister.username!!, "123456")
                            .dataConvert()
                    val end = System.currentTimeMillis()
                    Log.println(4, "123456789", "" + (end - start))
                    Toast.makeText(
                        this@WhatActivity,
                        userInfoRegister.username + "---" + userInfoLogin.username,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    //嵌套网络请求Async
    //  async 也是启动了一个单独的协程，这是一个轻量级的线程并与其它所有的协程      {一起并发}          的工作。
    //  delay 启动一个一会再去执行的协程
    //  不同之处在于 launch 返回一个 Job并且不附带任何结果值，
    //  而 async 返回一个 Deferred—— 一个轻量级的非阻塞 future， 这代表了一个将会在稍后提供结果的 promise。
    //  你可以使用 .await() 在一个延期的值上得到它的最终结果
    private fun getCoroutineScopeDataForNetMoreAsync() {
        getdataMoreAsync.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                // 在主线程开启协程
                launch(Dispatchers.Main) {
                    // IO 线程执行网络请求 等于内部retrofit进行了切换
                    //延迟值
                    val start = System.currentTimeMillis()
                    val userInfoRegister =
                        async {
                            RetrofitManager.service.register("wenwenwen2", "123456", "123456")
                                .dataConvert()
                        }
                    val userInfoLogin =
                        async {
                            RetrofitManager.service.login("zhang1", "123456").dataConvert()
                        }

                    val end = System.currentTimeMillis()
                    Log.println(4, "123456789", "" + (end - start))
                    Toast.makeText(
                        this@WhatActivity,
                        userInfoRegister.await().username
                                + "---" + userInfoLogin.await().username,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
