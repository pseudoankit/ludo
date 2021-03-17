package com.android.winmo.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.android.winmo.R
import com.android.winmo.databinding.ActivityLoginBinding
import com.android.winmo.ui.base.BaseActivity
import com.android.winmo.ui.game.ludo.ui.SelectGameActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val forgotPasswordFragment = ForgotPasswordFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnLoginForgotPassword.setOnClickListener { forgotPasswordBtn() }
        binding.btnLoginUser.setOnClickListener { openGameActivity() }
    }

    private fun openGameActivity() {
        startActivity(Intent(this, SelectGameActivity::class.java))
    }

    private fun forgotPasswordBtn() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_frag, forgotPasswordFragment)
            .addToBackStack(ForgotPasswordFragment::class.simpleName)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun activityBinding(layoutInflater: LayoutInflater) =
        ActivityLoginBinding.inflate(layoutInflater)
}