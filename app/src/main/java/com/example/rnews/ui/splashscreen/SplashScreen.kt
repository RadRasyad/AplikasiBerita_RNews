package com.example.rnews.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.rnews.databinding.ActivitySplashScreenBinding
import com.example.rnews.helper.ViewModelFactory
import com.example.rnews.ui.MainActivity
import com.example.rnews.ui.menu.MenuViewModel
import com.example.rnews.ui.menu.SettingPreferences
import com.example.rnews.ui.menu.dataStore

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val delay = 2000

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, delay.toLong())

        setTheme()

    }

    private fun setTheme() {
        val pref = SettingPreferences.getInstance(applicationContext.dataStore)
        val menuViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref)
        )[MenuViewModel::class.java]

        menuViewModel.getThemeSettings().observe(this, {
                theme: Int ->
            when (theme) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    menuViewModel.saveThemeSetting(0)
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    menuViewModel.saveThemeSetting(1)
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    menuViewModel.saveThemeSetting(2)
                }
            }
        })
    }
}