package com.enigma.givetip.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class TipCalculationRepository {
    private val savedTips = mutableMapOf<String, TipCalculation>()

    fun saveTipCalculation(tip: TipCalculation) {
        savedTips[tip.locationName] = tip
    }

    fun loadTipCalculationByName(locationName: String): TipCalculation? {

        return savedTips[locationName]
    }

    fun loadSavedTipCalculation(): LiveData<List<TipCalculation>> {
        val liveData = MutableLiveData<List<TipCalculation>>()
        liveData.value = savedTips.values.toList()
        return liveData
    }


}