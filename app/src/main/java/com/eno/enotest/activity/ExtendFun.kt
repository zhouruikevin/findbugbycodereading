package com.eno.enotest.activity

inline fun <T> T.guard(func: T.() -> Unit): T {
    if (this == null) {
        func()
    }
    return this
}

fun test() {
    print(1)
    return print(2)
}

class Extend() {
    init {

    }
}