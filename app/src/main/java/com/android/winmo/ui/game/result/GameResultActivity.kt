package com.android.winmo.ui.game.result

import android.os.Bundle
import android.view.LayoutInflater
import com.android.winmo.databinding.ActivityGameResultBinding
import com.android.winmo.ui.base.BaseActivity
import com.android.winmo.ui.game.ludo.ui.LudoActivity.WINNER
import kotlin.properties.Delegates

class GameResultActivity : BaseActivity<ActivityGameResultBinding>() {

    private var winner by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        displayWinner()
        binding.btnGrRestart.setOnClickListener { finish() }

    }

    private fun displayWinner() {
        binding.tvWinner.text = when (winner) {
            1 -> "Player One Won"
            2 -> "Player Two Won"
            3 -> "Player Three Won"
            else -> "Player Four Won"
        }
    }

    private fun init() {
        winner = intent.extras!!.getInt(WINNER)
    }

    override fun activityBinding(layoutInflater: LayoutInflater) =
        ActivityGameResultBinding.inflate(layoutInflater)

}