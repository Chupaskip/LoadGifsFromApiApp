package com.example.loadgifsfromapiapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.loadgifsfromapiapp.R
import com.example.loadgifsfromapiapp.databinding.ActivityMainBinding
import com.example.loadgifsfromapiapp.repository.GiphyRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment).navController
        val viewModelProviderFactory = GiphyViewModelProviderFactory(GiphyRepository())
        val viewModel = ViewModelProvider(this, viewModelProviderFactory)[GiphyViewModel::class.java]

    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }
}