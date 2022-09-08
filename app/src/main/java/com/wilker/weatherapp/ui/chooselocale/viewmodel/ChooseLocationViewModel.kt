package com.wilker.weatherapp.ui.chooselocale.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wilker.weatherapp.ui.chooselocale.viewmodel.ChooseLocationState.DISABLE_BUTTON
import com.wilker.weatherapp.ui.chooselocale.viewmodel.ChooseLocationState.ENABLE_BUTTON

class ChooseLocationViewModel: ViewModel() {

    val state = MutableLiveData<ChooseLocationState>()
    private val helper = ChooseLocationHelper()

    init {
        setupEnabledButton()
    }
    fun onLocationChanged(location: String){
        helper.location = location
        setupEnabledButton()
    }

    private fun setupEnabledButton(){
        statusButton(enableButton())
    }

    private fun enableButton() = helper.locationIsValid()

    private fun statusButton(enable: Boolean){
        val button = if (enable) ENABLE_BUTTON else DISABLE_BUTTON
        state.postValue(button)
    }
}