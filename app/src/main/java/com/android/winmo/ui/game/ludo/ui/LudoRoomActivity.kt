package com.android.winmo.ui.game.ludo.ui

import android.os.Bundle
import android.view.LayoutInflater
import com.android.winmo.databinding.ActivityLudoRoomBinding
import com.android.winmo.ui.base.BaseActivity
import com.android.winmo.ui.game.ludo.dialog.CreateRoomDialog
import com.android.winmo.ui.game.ludo.dialog.JoinRoomDialog

class LudoRoomActivity : BaseActivity<ActivityLudoRoomBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnCreateRoom.setOnClickListener { createRoomBtn() }
        binding.btnJoinRoom.setOnClickListener { joinRoomBtn() }
    }

    private fun joinRoomBtn() {
        JoinRoomDialog().show(supportFragmentManager,"Join Room")
    }

    private fun createRoomBtn() {
        CreateRoomDialog().show(supportFragmentManager,"Create Room")
    }

    override fun activityBinding(layoutInflater: LayoutInflater) =
        ActivityLudoRoomBinding.inflate(layoutInflater)

}