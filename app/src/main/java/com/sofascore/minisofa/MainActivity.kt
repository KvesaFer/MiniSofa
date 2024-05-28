package com.sofascore.minisofa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.sofascore.minisofa.databinding.ActivityMainBinding
import com.sofascore.minisofa.ui.DateAdapter
import com.sofascore.minisofa.ui.EventAdapter
import com.sofascore.minisofa.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var eventAdapter: EventAdapter
    private lateinit var dateAdapter: DateAdapter
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    private val displayDateFormat = SimpleDateFormat("EEE, dd.MM.yyyy.", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        setSupportActionBar(findViewById(R.id.toolbar))
        setupSportTabs()
        setupDateRecyclerView()
        setupRecyclerView()
        observeViewModel()

        loadDefaultEvents()
    }

    private fun setupClickListeners() {
        binding.toolbar.actionLeagues.setOnClickListener {
            startActivity(Intent(this, LeaguesActivity::class.java))
        }
    }

    private fun setupSportTabs() {
        val inflater = LayoutInflater.from(this)

        val tabFootball = binding.tabLayout.newTab()
        tabFootball.customView = getTabView(inflater, R.drawable.ic_football, "Football")
        binding.tabLayout.addTab(tabFootball)

        val tabBasketball = binding.tabLayout.newTab()
        tabBasketball.customView = getTabView(inflater, R.drawable.ic_basketball, "Basketball")
        binding.tabLayout.addTab(tabBasketball)

        val tabAmericanFootball = binding.tabLayout.newTab()
        tabAmericanFootball.customView = getTabView(inflater, R.drawable.ic_american_football, "Am. Football")
        binding.tabLayout.addTab(tabAmericanFootball)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab selection
                tab?.let {
                    val sport = when (it.position) {
                        0 -> "football"
                        1 -> "basketball"
                        2 -> "american_football"
                        else -> "football"
                    }
                    viewModel.setSport(sport)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupDateRecyclerView() {
        val calendar = Calendar.getInstance()
        val dates = mutableListOf<Date>()
        for (i in -7..7) {
            calendar.add(Calendar.DAY_OF_YEAR, i)
            dates.add(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, -i)  // Reset to today
        }

        dateAdapter = DateAdapter(dates) { selectedDate ->
            viewModel.setDate(SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(selectedDate))
            updateDateListSection(selectedDate)
        }

        binding.dateRecyclerView.apply {
            adapter = dateAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        val todayPosition = dates.indexOfFirst { date ->
            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date) ==
                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
        } + 2

        binding.dateRecyclerView.post {
            (binding.dateRecyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(todayPosition, binding.dateRecyclerView.width / 2)
        }
    }

    private fun getTabView(inflater: LayoutInflater, iconResId: Int, text: String): View {
        val view = inflater.inflate(R.layout.custom_tab_layout, null)
        val tabIcon = view.findViewById<ImageView>(R.id.tab_icon)
        val tabText = view.findViewById<TextView>(R.id.tab_text)
        tabIcon.setImageResource(iconResId)
        tabText.text = text
        return view
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter()
        binding.recyclerView.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.flatEventList.observe(this) { events ->
            if (events != null) {
                Log.d("MainActivity", "Received events: $events")
                eventAdapter.submitList(events)
                setNumberOfEvents(events.size)
            } else {
                Log.d("MainActivity", "No events found")
                setNumberOfEvents(0)
            }
        }
    }

    private fun updateDateListSection(date: Date) {
        val today = Calendar.getInstance().time
        val sectionDate = if (dateFormat.format(date) == dateFormat.format(today)) {
            "Today"
        } else {
            displayDateFormat.format(date)
        }
        setDateListSection(sectionDate)
    }

    private fun setDateListSection(date: String) {
        binding.dateListSection.text = date
    }

    private fun setNumberOfEvents(number: Int) {
        binding.numberOfEvents.text = "$number events"
    }

    private fun loadDefaultEvents() {
        viewModel.setSport("football")

        val today = Calendar.getInstance().time
        val formattedToday = dateFormat.format(today)
        viewModel.setDate(formattedToday)

        updateDateListSection(today)
    }
}