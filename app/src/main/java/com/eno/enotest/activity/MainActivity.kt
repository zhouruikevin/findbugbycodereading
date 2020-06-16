package com.eno.enotest.activity

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.eno.enotest.R
import com.eno.enotest.fragment.EnoFragment
import com.eno.enotest.fragment.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

const val FRAGMENT_INDEX = "fragment_index"
const val TAG = "TestByZR"

class MainActivity : BaseActivity(), View.OnClickListener {
    private lateinit var mTvHome: AppCompatTextView
    private lateinit var mTvMessage: AppCompatTextView
    private lateinit var mTvMine: AppCompatTextView
    private var mHomeFragment: Fragment? = null
    private var mMessageFragment: Fragment? = null
    private var mMineFragment: Fragment? = null
    private var indexAfter = -1

    /**
     * 两个任意一个为 True 操作 退出 再进入就闪退
     */
    private val isViewPageMore = true //是否 ViewPage+N个 Fragment
    private val isViewPage2More = false //是否 ViewPage2+N个 Fragment


    override fun bindLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        launch {
            Log.d(TAG, "initData: 111");
        }
        mTvHome = findViewById(R.id.tv_home)
        mTvMessage = findViewById(R.id.tv_message)
        mTvMine = findViewById(R.id.tv_mine)
        mTvHome.setOnClickListener(this)
        mTvMessage.setOnClickListener(this)
        mTvMine.setOnClickListener(this)
        mTvMine.getLocalVisibleRect(Rect())
        indexAfter = savedInstanceState?.getInt(FRAGMENT_INDEX, -1) ?: 0
        mMineFragment ?: return
        onCheckFragemnt(indexAfter)

    }

    val coro = CoroutineScope(Dispatchers.Main);
    fun testEscaping() {
        coro.launch(Dispatchers.IO) {
            delay(100)
            Log.d(TAG, "testEscaping: 开始执行网络请求")
        }
        Log.d(TAG, "testEscaping: 开始执行")
    }

    fun test() {
        val context = WeakReference(this@MainActivity);
        coro.launch {
            delay(1000)
            context.get()?.mTvMessage?.setText("1秒后调用,1秒内可能发生内存泄露")
        }
    }

    fun test2() {
        Log.d(TAG, "test: 222")
        val a = 2;
        val b = 1;
        assertAllTrue(lazy {
            Log.d(TAG, "test: 求值")
            a == b
        })
    }

    fun autoTest(closure: Lazy<Boolean>) {
        Log.d(TAG, "lazy函数被调用")
        if (closure.value) {
            Log.d(TAG, "autoTest:true")
        } else {
            Log.d(TAG, "autoTest: false")
        }
    }

    fun isTrue(): Boolean {
        Log.d(TAG, "求值..")
        return true;
    }

    // 调用
    fun testClosure() {
        autoTest(lazy { isTrue() })
    }

    fun returnFalse() = false.also { println("returnFalse called.") }
    fun returnTrue() = true.also { println("returnTrue called.") }
    fun assertAllTrue(vararg conditions: Lazy<Boolean>): Boolean {
        Log.d(TAG, "assertAllTrue: before")
        return conditions.all { it.value }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_home -> onCheckFragemnt(0)
            R.id.tv_message -> onCheckFragemnt(1)
            R.id.tv_mine -> onCheckFragemnt(2)
        }
        testClosure();
    }

    private fun onCheckFragemnt(i: Int) {
        var index = i
        if (index == -1) {
            index = 0
        }
        if (index == indexAfter) {
            return
        }
        indexAfter = index
        mTvHome.isSelected = 0 == index
        mTvMessage.isSelected = 1 == index
        mTvMine.isSelected = 2 == index
        val transaction = supportFragmentManager.beginTransaction()
        when (index) {
            0 -> {
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance()
                    transaction.add(R.id.main_layout, mHomeFragment!!)
                }
                showFragment(mHomeFragment)
            }
            1 -> {
                if (mMessageFragment == null) {
                    mMessageFragment = EnoFragment.newInstance(index, "消息")
                    transaction.add(R.id.main_layout, mMessageFragment!!)
                }
                showFragment(mMessageFragment)
            }
            2 -> {
                if (mMineFragment == null) {
                    mMineFragment = EnoFragment.newInstance(index, "我的")
                    transaction.add(R.id.main_layout, mMineFragment!!)
                }
                showFragment(mMineFragment)
            }
        }
        transaction.commitAllowingStateLoss()
    }

    /**
     * 显示对应的Fragment
     *
     * @param fragment
     */
    private fun showFragment(fragment: Fragment?) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            hideAllFragment(transaction)
            transaction.show(fragment)
            transaction.commitAllowingStateLoss()
        }
    }

    /**
     * 隐藏所有Fragment
     *
     * @param transaction
     */
    private fun hideAllFragment(transaction: FragmentTransaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment!!)
        }
        if (mMessageFragment != null) {
            transaction.hide(mMessageFragment!!)
        }
        if (mMineFragment != null) {
            transaction.hide(mMineFragment!!)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(FRAGMENT_INDEX, indexAfter)
        Log.d(TAG, "onSaveInstanceState: ")
    }
}