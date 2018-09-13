package com.wen.mybehaviordemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationSet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * var: 变量(读写) val：常量(只读)
     * const实际上java的static final。 需要注意的是，Const只能是kotlin的string和基本类型。
     */
    companion object {
        const val MESSAGE_WHAT = 1001
    }

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                MainActivity.MESSAGE_WHAT -> {
                    gotoIntent()
                }
                else -> {
                }
            }
        }
    }

    private fun gotoIntent() {
        val intent = Intent(this, Main2Activity().javaClass)
        this.startActivity(intent)
        //写两个空动画 去除两个activity间明显的卡顿效果
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_in)
    }

    var rotate3dAnimationX: Rotate3dAnimation? = null
    var ll: View? = null
    var mWidth: Int = 0
    var mHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        val screenBounds = ScreenUtils.getScreenBounds(this)
        mWidth = screenBounds[0]
        mHeight = screenBounds[1]
        main_layout.setOnClickListener(View.OnClickListener {
            ll = findViewById<View>(R.id.main_layout)
            val animationSet = AnimationSet(true)
            val depthZ = 450f
            rotate3dAnimationX = Rotate3dAnimation()
            rotate3dAnimationX?.Rotate3dAnimation(0F, -90F, mWidth/2.0F, mHeight/2.0F, depthZ, true)
            animationSet.addAnimation(rotate3dAnimationX)
            animationSet.duration = 250
            animationSet.fillAfter = true
            animationSet.interpolator = AccelerateDecelerateInterpolator()
            ll?.startAnimation(animationSet)
            val mMessage = Message.obtain()
            mMessage.what = 1001
            // 使用sendEmptyMessageDelayed延时1s后发送一条消息
            mHandler.sendEmptyMessageDelayed(MainActivity.MESSAGE_WHAT, 240)
        })
    }

    //在界面执行此方法时 把界面恢复原来的状态  暂时没想到其他好的方法
    override fun onStop() {
        val animationSet = AnimationSet(true)
        rotate3dAnimationX?.Rotate3dAnimation(-90F, 0F, mWidth/2.0F, mHeight/2.0F, 0F, true)
        animationSet.duration = 0
        animationSet.addAnimation(rotate3dAnimationX)
        ll?.startAnimation(animationSet)
        super.onStop()
    }
}

