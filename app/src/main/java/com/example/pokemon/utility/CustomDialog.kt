package com.example.pokemon.utility

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.pokemon.R
import javax.inject.Inject

class CustomDialog @Inject constructor(
    activity: Activity,
    cancelable: Boolean
) {
    private val dialog: AlertDialog

    init {
        val viewGroup: ViewGroup = activity.findViewById(android.R.id.content)
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.activity_loading, viewGroup, false)
        val builder = AlertDialog.Builder(activity)
            .setView(view)
        builder.setCancelable(cancelable)
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun showDialog() {
        dialog.show()
    }

    fun dismisDialog() {
        dialog.dismiss()
    }

}