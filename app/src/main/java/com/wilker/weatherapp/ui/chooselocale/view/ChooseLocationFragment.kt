package com.wilker.weatherapp.ui.chooselocale.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.wilker.weatherapp.databinding.FragmentChooseLocaleBinding
import com.wilker.weatherapp.ui.chooselocale.viewmodel.ChooseLocationState
import com.wilker.weatherapp.ui.chooselocale.viewmodel.ChooseLocationState.*
import com.wilker.weatherapp.ui.chooselocale.viewmodel.ChooseLocationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ChooseLocationFragment : Fragment() {

    private val viewModel: ChooseLocationViewModel by viewModels()

    private val binding by lazy {
        FragmentChooseLocaleBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupViewState()
        onLocationChanged()
        setupClickButtonSearch()

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

    @SuppressLint("NewApi")
    private fun setEnableButton(){
        binding.btnSearch.setEnabledButton(true)
    }

    @SuppressLint("NewApi")
    private fun setDisableButton(){
        binding.btnSearch.setEnabledButton(false)
    }

    @SuppressLint("NewApi")
    private fun setupClickButtonSearch(){
        binding.btnSearch.onClick {
            binding.btnSearch.progress()
            lifecycleScope.launch {
                delay(3000)
                binding.btnSearch.reverseAnimation {
                    Toast.makeText(requireContext(), "Demostração da animação", Toast.LENGTH_SHORT ).show()
                }
            }
        }
    }

}
