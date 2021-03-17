package com.android.winmo.ui.game.ludo.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.android.winmo.R
import com.android.winmo.databinding.DialogCreateRoomBinding
import com.android.winmo.ui.game.ludo.constant.LudoConstants
import com.android.winmo.ui.game.ludo.constant.LudoConstants.ROOM_ID
import com.android.winmo.ui.game.ludo.constant.LudoConstants.ROOM_OWNER
import com.android.winmo.ui.game.ludo.ui.UserRoomActivity
import com.android.winmo.util.randomId
import com.android.winmo.util.toast


class CreateRoomDialog : DialogFragment() {

    private lateinit var binding: DialogCreateRoomBinding
    private lateinit var v: View
    private lateinit var tvRoomKey: TextView
    private lateinit var shareWA: TextView
    private lateinit var roomKey: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        v = activity!!.layoutInflater.inflate(R.layout.dialog_create_room, null)
        init()
        val builder = builder()

        shareWA.setOnClickListener { shareToWhatsapp() }

        return builder.create()
    }

    private fun builder(): AlertDialog.Builder {
        val builder = AlertDialog.Builder(activity)
        builder.setView(v)

        builder.setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog!!.dismiss() }
        builder.setPositiveButton("create") { _, _ -> createRoomBtn() }

        return builder
    }

    private fun createRoomBtn() {
        val intent = Intent(activity, UserRoomActivity::class.java)
        intent.putExtra(ROOM_ID, roomKey)
        intent.putExtra(ROOM_OWNER,true)
        startActivity(intent)
    }

    private fun shareToWhatsapp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.setPackage("com.whatsapp")
        intent.putExtra(Intent.EXTRA_TEXT, "Enter this room key to play Ludo-Winmo:- $roomKey")
        try {
            activity!!.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            activity!!.toast("Whatsapp not installed.")
        }
    }

    private fun init() {
        roomKey = randomId()
        tvRoomKey = v.findViewById(R.id.tvRoomKey)
        shareWA = v.findViewById(R.id.shareToWhatsapp)
        tvRoomKey.text = roomKey
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}