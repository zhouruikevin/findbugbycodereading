package com.eno.enotest.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.eno.enotest.R
import com.eno.enotest.manager.AppManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class SplashActivity : BaseActivity() {

    class MyHandler : Handler {
        var context: WeakReference<Context>

        constructor(context: Context) {
            this.context = WeakReference(context)
        }

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SKIP_MAIN -> {
                    context.get()?.apply {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }
                else -> throw IllegalArgumentException()
            }
            super.handleMessage(msg)
        }
    }

    private val mHandler = MyHandler(this)

    private fun toNextActivity() {
        startActivity(Intent(this, MainActivity::class.java));
        AppManager.getAppManager().finishActivity(SplashActivity::class.java);
    }

    override fun bindLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData(savedInstanceState: Bundle?) {
        Thread() {
            mHandler.sendEmptyMessageDelayed(SKIP_MAIN, 5 * 1000.toLong())
        }.start()

        CoroutineScope(Dispatchers.Default).launch {
            delay(5000)
            mHandler.sendMessage(Message.obtain().apply { what = SKIP_MAIN })
        }
//
//        mHandler.sendEmptyMessageDelayed(SKIP_MAIN, 5 * 1000.toLong())
    }

    suspend fun testMemLeak() = launch(Dispatchers.Default) {
        delay(5000)
        mHandler.sendMessage(Message.obtain().apply { what = SKIP_MAIN })
    }

    companion object {
        const val SKIP_MAIN = 1000
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        isFinishing
    }

    val coroutineScope = CoroutineScope(Dispatchers.Default);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launch {
            delay(5000)
            mHandler.sendMessage(Message.obtain().apply { what = SKIP_MAIN })
        }
//        coroutineScope.launch {
//            delay(5000)
//            mHandler.sendMessage(Message.obtain().apply { what = SKIP_MAIN })
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        coroutineScope.cancel()
    }

}