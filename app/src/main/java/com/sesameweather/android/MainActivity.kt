package com.sesameweather.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * 项目分层  ： UI 层 ; ViewModel层 ; 仓库层 ；
 * UI 层 --> ViewModel 层 --> 仓库层
 * ui层 指向 viewModel 层 ，表示ui层持有 viewModel 层的引用， viewModel层不能持有 Ui层的引用
 * ViewModel 层 指向 --> 仓库层，  表示 viewModel 层持有 仓库层的引用
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}