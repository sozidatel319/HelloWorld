package com.mikhail.weatherclient.presentation.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mikhail.weatherclient.R
import com.mikhail.weatherclient.presentation.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {
    private lateinit var themeColorSwitcher: Switch
    private lateinit var unitOfMeasureSwitcher: Switch
    private lateinit var okButton: Button
    private lateinit var settingsViewModel: SettingsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        init(view)

        settingsViewModel.isFahrenheitLiveData.observe(
            viewLifecycleOwner
        ) { isFahrenheit: Boolean ->
            unitOfMeasureSwitcher.isChecked = isFahrenheit
        }

        settingsViewModel.isDarkThemeLiveData.observe(viewLifecycleOwner) {
            themeColorSwitcher.isChecked = it
        }

        initListeners()
    }

    private fun init(view: View) {
        themeColorSwitcher = view.findViewById(R.id.themecolor)
        unitOfMeasureSwitcher = view.findViewById(R.id.unitofmeasure)
        okButton = view.findViewById(R.id.okButton)
    }

    private fun initListeners() {
        unitOfMeasureSwitcher.setOnClickListener { unitOfMeasureView: View ->
            settingsViewModel.saveUnitOfMeasure(
                (unitOfMeasureView as Switch).isChecked
            )
        }

        themeColorSwitcher.setOnClickListener { themeColorView ->

            settingsViewModel.saveTheme((themeColorView as Switch).isChecked)
            requireActivity().recreate()
        }

        okButton.setOnClickListener { v: View? ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.navHostFragmentRoot, HomeFragment()).commit()
        }
    }
}