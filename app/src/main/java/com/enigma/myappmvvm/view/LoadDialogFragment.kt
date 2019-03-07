package com.enigma.givetip.view

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import com.enigma.givetip.R
import com.enigma.givetip.viewmodel.CalculatorViewModel
import kotlinx.android.synthetic.main.saved_tip_calculations_list.view.*

class LoadDialogFragment : DialogFragment() {
    interface Callback {
        fun onTipSelected(name: String)
    }

    private var loadTipCallback: Callback? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loadTipCallback = context as? Callback
    }

    override fun onDetach() {
        super.onDetach()
        loadTipCallback = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = context?.let { ctx ->
            AlertDialog.Builder(ctx)
                .setView(createView(ctx))
                .setNegativeButton(R.string.action_cancel, null)
                .create()
        }
        return dialog!!
    }

    private fun createView(ctx: Context): View {
        val locationPriceRV = LayoutInflater.from(ctx).inflate(R.layout.saved_tip_calculations_list, null)
            .recycler_saved_calculations
        locationPriceRV.setHasFixedSize(true)
        locationPriceRV.addItemDecoration(DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL))
        val summaryAdapter = TipSummaryAdapter {
            loadTipCallback?.onTipSelected(it.locationName)
            dismiss()
        }
        locationPriceRV.adapter = summaryAdapter
        val vm = ViewModelProviders.of(activity!!).get(CalculatorViewModel::class.java)
        vm.loadSaveTipCalculationSummaries().observe(this, Observer {
            if (it != null) {
                summaryAdapter.updateList(it)
            }
        })
        return locationPriceRV
    }
}