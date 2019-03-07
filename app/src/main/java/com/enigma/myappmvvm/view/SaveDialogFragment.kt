package com.enigma.givetip.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.telecom.Call
import android.view.View
import android.widget.EditText
import com.enigma.givetip.R

class SaveDialogFragment : DialogFragment() {

    interface Callback {
        fun onSaveTip(locationName: String)
    }

    private var saveTipCallBack: SaveDialogFragment.Callback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        saveTipCallBack = context as? Callback
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val saveDialog = context?.let { ctx ->
            val editText = EditText(ctx)
            editText.id = viewId
            editText.hint = ctx.getString(R.string.hint_location)
            AlertDialog.Builder(ctx)
                .setView(editText)
                .setNegativeButton(R.string.action_cancel, null)
                .setPositiveButton(R.string.action_save, { _, _ -> onSave(editText) })
                .create()
        }
        return saveDialog!!
    }

    private fun onSave(editText: EditText) {
        val locationName = editText.text.toString()
        if (locationName.isNotEmpty()) {
            saveTipCallBack?.onSaveTip(locationName)
        }
    }

    companion object {
        val viewId = View.generateViewId()
    }
    override fun onDetach() {
        super.onDetach()
        saveTipCallBack = null
    }

}