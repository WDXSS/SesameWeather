package com.sesameweather.android.ui.place

import android.text.Editable
import android.text.TextWatcher

/**
 * @author zhouchao
 * @date 2020/7/16
 */

fun main() {
    example { s: String, i: Int ->
        println("s = $s , i = $i")
    }
}

fun example(func: (String, Int) -> Unit) {
}

inline fun testAddTextChangedListener(
    crossinline beforeTextChanged: (text: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ ->

    },
    crossinline onTextChanged: (text: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { _, _, _, _ -> },
    crossinline afterTextChanged: (text: Editable?) -> Unit = {}
): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s)
            //invoke 函数 函数类型，lambda 调用执行
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            onTextChanged.invoke(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            beforeTextChanged.invoke(s, start, before, count)
        }

    }
    return textWatcher
}