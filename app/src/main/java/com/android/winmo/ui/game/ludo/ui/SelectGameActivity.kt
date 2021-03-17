package com.android.winmo.ui.game.ludo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.android.winmo.databinding.ActivitySelectGameBinding
import com.android.winmo.ui.base.BaseActivity

class SelectGameActivity : BaseActivity<ActivitySelectGameBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnPlayLocal.setOnClickListener { startActivity(Intent(this,LudoActivity::class.java)) }
        binding.btnPlayOnline.setOnClickListener { startActivity(Intent(this,LudoRoomActivity::class.java)) }

    }

    override fun activityBinding(layoutInflater: LayoutInflater) =
        ActivitySelectGameBinding.inflate(layoutInflater)
}