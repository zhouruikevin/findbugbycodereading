package com.eno.enotest

import android.graphics.Point
import android.util.Log
import org.junit.Test

const val URL = "https://www.google.com/";

class KotlinExample {
    @Test
    fun test() {
        val text: String = """
    |Tell me and I forget.
    |Teach me and I remember.
    |Involve me and I learn.
    |(Benjamin Franklin)
    """
        // 变量
        var v = "8"
        // 不可变 参数默认是val类型
        printFinal(Point(10, 20))
        // 常量
        doHttpFetch(URL)
        // 静态对象即Object class
        Log.d("TAG", "nnerComp.value is ${InnerComp.value}")
        // [1,10]
        for (i in 1..10) {
            System.out.println(i);
        }
        // [1,10)
        for (i in 1 until 10) {

        }
    }

    fun printFinal(point: Point) {        // 参数point默认是val类型
        Log.d("TAG", "printFinal is ($point.x,$point.y)")
    }

    fun doHttpFetch(url: String) {
        var dyn: dynamic = ""
        dyn = 1
    }


    companion object InnerComp {
        val value = 10
    }
}