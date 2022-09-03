package com.wilker.weatherapp.ui.chooselocale.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wilker.weatherapp.databinding.FragmentChooseLocaleBinding


class ChooseLocationFragment : Fragment() {

    private lateinit var binding: FragmentChooseLocaleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseLocaleBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}
