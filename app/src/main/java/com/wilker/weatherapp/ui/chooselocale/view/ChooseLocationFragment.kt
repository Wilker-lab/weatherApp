package com.wilker.weatherapp.ui.chooselocale.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.wilker.weatherapp.databinding.FragmentChooseLocaleBinding
import com.wilker.weatherapp.ui.chooselocale.viewmodel.ChooseLocationState
import com.wilker.weatherapp.ui.chooselocale.viewmodel.ChooseLocationState.*
import com.wilker.weatherapp.ui.chooselocale.viewmodel.ChooseLocationViewModel


class ChooseLocationFragment : Fragment() {

    private val viewModel: ChooseLocationViewModel by viewModels()

    private lateinit var binding: FragmentChooseLocaleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseLocaleBinding.inflate(layoutInflater, container, false)
        setupViewState()
        onLocationChanged()

        return binding.root
    }

    private fun setupViewState(){
        viewModel.state.observe(viewLifecycleOwner){
            val state = it ?: return@observe

            when(state){
                SUCCESS -> TODO()
                ERROR -> TODO()
                LOAD -> TODO()
                ENABLE_BUTTON -> setEnableButton()
                DISABLE_BUTTON -> setDisableButton()
            }
        }
    }

    private fun onLocationChanged(){
        binding.autoCompleteCity.doOnTextChanged { city, _, _, _ ->
            viewModel.onLocationChanged(city.toString())
        }
    }

    private fun setEnableButton(){
        binding.btnSearch.isEnabled = true
    }

    private fun setDisableButton(){
        binding.btnSearch.isEnabled = false
    }

}
