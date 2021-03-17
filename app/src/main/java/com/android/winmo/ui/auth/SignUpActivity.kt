package com.android.winmo.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.winmo.R
import com.android.winmo.databinding.ActivityLoginBinding
import com.android.winmo.databinding.ActivitySignUpBinding
import com.android.winmo.ui.base.BaseActivity

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun activityBinding(layoutInflater: LayoutInflater) =
        ActivitySignUpBinding.inflate(layoutInflater)
}