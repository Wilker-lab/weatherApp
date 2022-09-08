package com.wilker.weatherapp.ui.chooselocale.viewmodel

class ChooseLocationHelper {
    var location = EMPTY

    fun locationIsValid() = location.isNotEmpty()

    private companion object{
        const val EMPTY = ""
    }
}