package com.example.rnews.ui.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rnews.R
import com.example.rnews.databinding.FragmentMenuBinding
import com.example.rnews.helper.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private var _binding: FragmentMenuBinding? = null
private val binding get() = _binding!!

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.theme.setOnClickListener { themeDialog() }

    }

    private fun themeDialog() {

        val pref = SettingPreferences.getInstance(requireActivity().dataStore)
        val menuViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(MenuViewModel::class.java)

        val singleItems = arrayOf("Light", "Dark", "Default")
        var choosed = menuViewModel.getThemeSettings()
        val checkedItem = 2

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.theme))

            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                // Respond to positive button press
            }
            // Single-choice items (initialized with checked item)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                // Respond to item chosen
                when(which) {
                    0 ->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        menuViewModel.saveThemeSetting(0)
                    }
                    1 ->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        menuViewModel.saveThemeSetting(1)

                    }
                    2 ->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        menuViewModel.saveThemeSetting(2)

                    }
                }

            }
            .show()
    }


}