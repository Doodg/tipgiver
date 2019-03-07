package com.enigma.givetip.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.util.Log
import com.enigma.givetip.R
import com.enigma.givetip.model.Calculator
import com.enigma.givetip.model.TipCalculation
import java.util.*

class CalculatorViewModel @JvmOverloads constructor(val app: Application, val calculator: Calculator = Calculator()) :
    ObservableCallback(app) {
    private var lastTipCalculation = TipCalculation()
    var inputCheckAmount = ""
    var inputTipPercentage = ""
    val outputCheckAmount
        get() = getApplication<Application>().getString(
            R.string.dollar_amount,
            lastTipCalculation.checkAmount
        )
    val outputTipAmount
        get() = getApplication<Application>().getString(
            R.string.dollar_amount,
            lastTipCalculation.tipAmount
        )
    val outputTotalDollarAmount
        get() = getApplication<Application>().getString(
            R.string.dollar_amount,
            lastTipCalculation.grandTotal
        )

    val locationName get() = lastTipCalculation.locationName

    init {
        updateOutPuts(TipCalculation())
    }

    private fun updateOutPuts(tipCalculation: TipCalculation) {
        lastTipCalculation = tipCalculation
        notifyChange()
    }

    fun calculateTip() {
        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPct = inputTipPercentage.toIntOrNull()
        if (checkAmount != null && tipPct != null) {
            updateOutPuts(calculator.calculateTip(checkAmount, tipPct))
        }
    }

    fun saveCurrentTip(name: String) {
        val tipToSave = lastTipCalculation.copy(locationName = name)
        calculator.saveTipCalculation(tipToSave)
        updateOutPuts(tipToSave)
    }

    fun loadSaveTipCalculationSummaries(): LiveData<List<TipCalculationSummaryItem>> {
        return Transformations.map(calculator.loadSavedTipCalculations(), {
            it.map {
                TipCalculationSummaryItem(
                    it.locationName,
                    getApplication<Application>().getString(R.string.dollar_amount, it.grandTotal)
                )
            }
        })
    }

    fun loadTipCalculation(name: String) {
        val tc = calculator.loadTipCalculationByLocationName(name).let {
            inputCheckAmount = it?.checkAmount.toString()
            inputTipPercentage = it?.tipPct.toString()
            updateOutPuts(it!!)
            notifyChange()
        }
    }

}