package me.sebhernoux.dmii.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.sebhernoux.dmii.fragments.Operation

class ComputeViewModel: ViewModel() {
    private val _resultatLiveData = MutableLiveData<Double>()
    val resultatLiveData: LiveData<Double>
        get() = _resultatLiveData

    fun calculate(value1: Double, value2: Double, operation: Operation) {
        _resultatLiveData.value = when(operation) {
            Operation.SUM -> value1.plus(value2)
            Operation.PRODUCT -> value1 * value2
            Operation.MINUS -> value1.minus(value2)
            Operation.DIVISE -> value1 / value2
        }
    }

}