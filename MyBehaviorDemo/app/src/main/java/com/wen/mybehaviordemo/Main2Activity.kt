package com.wen.mybehaviordemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationSet

class Main2Activity : AppCompatActivity() {

    /**
     * var: 变量(读写) val：常量(只读)
     * const实际上java的static final。 需要注意的是，Const只能是kotlin的string和基本类型。
     */
    companion object {
        const val MESSAGE_WHAT = 1001
    }

    var mWidth: Int = 0
    var mHeight: Int = 0

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                Main2Activity.MESSAGE_WHAT -> {
                    val ll = findViewById<View>(R.id.main2_layout)
                    //在此处练习了下动画集合的用法  可直接使用rotate3dAnimationX动画
                    val animationSet = AnimationSet(true)
                    val depthZ = 450f
                    val rotate3dAnimationX = Rotate3dAnimation()
                    rotate3dAnimationX.Rotate3dAnimation(-270F, -360F, mWidth/2.0F, mHeight/2.0F, depthZ, false)
                    animationSet.addAnimation(rotate3dAnimationX)
                    animationSet.duration = 250
                    animationSet.fillAfter = true
                    animationSet.interpolator = AccelerateDecelerateInterpolator()
                    ll?.startAnimation(animationSet)
                }
                else -> {
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main2)
        val screenBounds = ScreenUtils.getScreenBounds(this)
        mWidth = screenBounds[0]
        mHeight = screenBounds[1]
        val mMessage = Message.obtain()
        mMessage.what = 1001
        // 使用sendEmptyMessageDelayed延时1s后发送一条消息
        mHandler.sendEmptyMessageDelayed(Main2Activity.MESSAGE_WHAT, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_in)
    }
}
