package com.android.winmo.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.android.winmo.data.UserPreferences

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB
    protected lateinit var userPrefs: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = activityBinding(layoutInflater)
        setContentView(binding.root)
        userPrefs = UserPreferences(this)
    }

    abstract fun activityBinding(layoutInflater: LayoutInflater): VB

}