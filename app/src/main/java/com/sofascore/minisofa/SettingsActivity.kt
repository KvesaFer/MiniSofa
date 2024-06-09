package com.sofascore.minisofa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sofascore.minisofa.databinding.ActivitySettingsBinding
import com.sofascore.minisofa.utils.SettingsManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("SettingsActivity", "onCreate called")
        if (SettingsManager.isDarkTheme(this)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Log.d("SettingsActivity", "Dark theme applied")
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Log.d("SettingsActivity", "Light theme applied")
        }

        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarSettings.root.findViewById(R.id.toolbar))
        Log.d("SettingsActivity", "Toolbar set")

        val tw: TextView = binding.toolbarSettings.root.findViewById(R.id.tw_leagues)
        tw.text = "Settings"
        Log.d("SettingsActivity", "Toolbar title set to 'Settings'")

        val backButton: ImageView = binding.toolbarSettings.root.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            Log.d("SettingsActivity", "Finished; Back Button clicked")
            finish()
        }

        // Set initial states based on preferences
        binding.lightThemeRadio.isChecked = !SettingsManager.isDarkTheme(this)
        binding.darkThemeRadio.isChecked = SettingsManager.isDarkTheme(this)
        binding.euDateRadio.isChecked = SettingsManager.isEUDateFormat(this)
        binding.usDateRadio.isChecked = !SettingsManager.isEUDateFormat(this)

        Log.d("SettingsActivity", "Initial states set based on preferences")

        // Radio button logic
        setupRadioButtons()
    }

    private fun setupRadioButtons() {
        binding.lightThemeRadio.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("SettingsActivity", "Light theme selected")
                SettingsManager.setTheme(this, false)
                binding.darkThemeRadio.isChecked = false // Ensure dark theme radio is unchecked
                Log.d("SettingsActivity", "Dark theme radio unchecked")
                triggerRebirth(this)
            }
        }

        binding.darkThemeRadio.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("SettingsActivity", "Dark theme selected")
                SettingsManager.setTheme(this, true)
                binding.lightThemeRadio.isChecked = false // Ensure light theme radio is unchecked
                Log.d("SettingsActivity", "Light theme radio unchecked")
                triggerRebirth(this)
            }
        }

        binding.euDateRadio.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("SettingsActivity", "EU date format selected")
                SettingsManager.setDateFormat(this, true)
                binding.usDateRadio.isChecked = false // Ensure US date radio is unchecked
                Log.d("SettingsActivity", "US date radio unchecked")
                triggerRebirth(this)
            }
        }

        binding.usDateRadio.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("SettingsActivity", "US date format selected")
                SettingsManager.setDateFormat(this, false)
                binding.euDateRadio.isChecked = false // Ensure EU date radio is unchecked
                Log.d("SettingsActivity", "EU date radio unchecked")
                triggerRebirth(this)
            }
        }
    }

    fun triggerRebirth(context: Context) {
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        // Required for API 34 and later
        // Ref: https://developer.android.com/about/versions/14/behavior-changes-14#safer-intents
        mainIntent.setPackage(context.packageName)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }
}
