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

class SuspendActivity : AppCompatActivity(), CoroutineScope {

    //CoroutineContext.Element
    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var suspend: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suspend)

        job = Job()

        suspend = findViewById<View>(R.id.suspend) as TextView

        suspendCoroutineScope()

//        val async = async { }
//        async.await()
    }


    //我们挂起的对象是协程。
    //线程的代码在到达 suspend 函数的时候被掐断，接下来协程会从这个 suspend 函数开始继续往下执行，不过是在指定的线程。
    //Dispatchers.Main：Dispatchers.IO：Dispatchers.Default：
    //紧接着在 suspend 函数执行完成之后，协程会自动帮我们把线程再切回来。
    private fun suspendCoroutineScope() {

        suspend.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                launch(Dispatchers.Main) {
                    // 在 UI 线程开始
                    //withContext 这个函数可以切换到指定的线程
                    val message = getMessage()
                    Toast.makeText(this@SuspendActivity, message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    suspend fun getMessage(): String
    {
        val message = withContext(Dispatchers.IO) {
            //切换到 IO 线程，并在执行完成后切回 UI 线程
            val currentThread = Thread.currentThread()
            "我是在子线程中返回的消息"
        }
        return message
    }

// --->Kotlin 中所谓的挂起，就是一个稍后会被自动切回来的线程调度操作。

//    这个「切回来」的动作，在 Kotlin 里叫做 resume，恢复。协程挂起之后是需要恢复到原本的线程。
//    而恢复这个功能是协程的，如果你不在协程里面调用，恢复这个功能没法实现，所以kotlin语言直接就报错了。
//    再细想下这个逻辑：一个挂起函数要么在协程里被调用，要么在另一个挂起函数里被调用，那么它其实直接或者间接地，总是会在一个协程里被调用的。
//    所以，要求 suspend 函数只能在协程里或者另一个 suspend 函数里被调用，还是为了要让协程能够在 suspend 函数切换线程之后再切回来。

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
