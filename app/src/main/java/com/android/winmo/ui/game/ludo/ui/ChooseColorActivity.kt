package com.android.winmo.ui.game.ludo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.android.winmo.databinding.ActivityChooseColorBinding
import com.android.winmo.ui.base.BaseActivity
import com.android.winmo.ui.game.ludo.constant.LudoConstants.ROOM_ID
import com.android.winmo.ui.game.ludo.constant.LudoConstants.SELECTED_PLAYER

class ChooseColorActivity : BaseActivity<ActivityChooseColorBinding>() {

    private lateinit var roomKey : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        roomKey = intent.extras!!.getString(ROOM_ID)!!

        binding.player1.setOnClickListener { openLudoActivity(1) }
        binding.player2.setOnClickListener { openLudoActivity(2) }
        binding.player3.setOnClickListener { openLudoActivity(3) }
        binding.player4.setOnClickListener { openLudoActivity(0) }

    }

    private fun openLudoActivity(playerNo: Int){
        val intent = Intent(this,LudoMultiPlayerActivity::class.java)
        intent.putExtra(SELECTED_PLAYER,playerNo)
        intent.putExtra(ROOM_ID,roomKey)
        startActivity(intent)
    }

    override fun activityBinding(layoutInflater: LayoutInflater) =
        ActivityChooseColorBinding.inflate(layoutInflater)
}