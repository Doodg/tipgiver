package com.enigma.givetip.model

import android.arch.lifecycle.LiveData
import java.math.RoundingMode

class Calculator(val tipRepository: TipCalculationRepository = TipCalculationRepository()) {
    fun calculateTip(checkAmount: Double, tipPct: Int): TipCalculation {
        val tipAmount = checkAmount * (tipPct.toDouble() / 100.0)
            .toBigDecimal()
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()

        val grandTotal = checkAmount + tipAmount
        return TipCalculation(
            checkAmount = checkAmount,
            tipPct = tipPct,
            tipAmount = tipAmount,
            grandTotal = grandTotal
        )
    }

    fun saveTipCalculation(tip: TipCalculation) {
        tipRepository.saveTipCalculation(tip)
    }

    fun loadTipCalculationByLocationName(locationName: String): TipCalculation? {
        return tipRepository.loadTipCalculationByName(locationName)
    }

    fun loadSavedTipCalculations(): LiveData<List<TipCalculation>> {
        return tipRepository.loadSavedTipCalculation()
    }
}