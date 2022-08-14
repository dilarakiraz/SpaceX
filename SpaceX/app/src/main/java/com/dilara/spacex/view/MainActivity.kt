package com.dilara.spacex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.dilara.spacex.R
import com.dilara.spacex.databinding.ActivityMainBinding
import com.dilara.spacex.viewmodel.FavoriteRocketsViewModel
import com.dilara.spacex.viewmodel.RocketsDetailViewModel
import com.dilara.spacex.viewmodel.RocketsViewModel
import com.dilara.spacex.viewmodel.UpcomingLaunchesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.setupWithNavController(
            Navigation.findNavController(
                this,R.id.mainFragment
            )
        )
    }
}