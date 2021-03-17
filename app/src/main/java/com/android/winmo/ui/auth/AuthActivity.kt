package com.android.winmo.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.android.winmo.databinding.ActivityAuthBinding
import com.android.winmo.ui.base.BaseActivity
import com.android.winmo.ui.game.ludo.ui.SelectGameActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var loginButton: LoginButton
    private lateinit var fbCallbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fbCallbackManager = CallbackManager.Factory.create()
        configGoogleSignIn()

        binding.btnAuthLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnAuthSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.btnAuthGoogle.setOnClickListener {
            startActivityForResult(mGoogleSignInClient.signInIntent, RC_GOOGLE)
        }
        fbLogin()
    }

    private fun fbLogin() {

        loginButton = binding.btnAuthFb
        loginButton.setReadPermissions("email", "public_profile")
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        LoginManager.getInstance().registerCallback(fbCallbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    openGameActivity()
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })
    }

    private fun openGameActivity() {
        startActivity(Intent(this, SelectGameActivity::class.java))
        finish()
    }

    private fun onGoogleSignInSuccess(account: GoogleSignInAccount) {
        openGameActivity()
    }

    private fun configGoogleSignIn() {
        //creating google sign in client
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data!!)
            try {
                val account = task.getResult(ApiException::class.java)!!
                onGoogleSignInSuccess(account)

            } catch (e: ApiException) {
                //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }
        } else {
            fbCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    override fun onStart() {
        super.onStart()

        //if logged in with google
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            openGameActivity()
        }

        //if logged in with fb
        val accessTokenFb = AccessToken.getCurrentAccessToken()
        val isLoggedInFb = accessTokenFb != null && !accessTokenFb.isExpired
        if(isLoggedInFb) openGameActivity()

    }

    companion object {
        private const val RC_GOOGLE = 123
    }

    override fun activityBinding(layoutInflater: LayoutInflater) =
        ActivityAuthBinding.inflate(layoutInflater)
}