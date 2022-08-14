package com.dilara.spacex.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.dilara.spacex.R
import com.dilara.spacex.databinding.FragmentLaunchDetailBinding
import com.dilara.spacex.util.Status
import com.dilara.spacex.util.downloadImage
import com.dilara.spacex.view.MainActivity
import com.dilara.spacex.viewmodel.LaunchDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchDetailFragment : Fragment(R.layout.fragment_launch_detail) {

    private var _binding:FragmentLaunchDetailBinding?=null
    private val binding get() =_binding!!
    private val viewModel:LaunchDetailViewModel by viewModels()
    private val args:LaunchDetailFragmentArgs by navArgs()
    private val TAG="LaunchDetailFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentLaunchDetailBinding.bind(view)

        initObservers()
        backButton()
    }

    private fun initObservers(){
        args.upcoming.id?.let {
            viewModel.getLaunchDetail(it).observe(viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let {
                                binding.apply {
                                    textName.text = it.name
                                    textFlightNumber.text = it.flightNumber.toString()
                                    textDate.text = it.date
                                    if (it.links.patch.small != null) {
                                        imgLaunch.visibility = View.VISIBLE
                                        imgLaunch.downloadImage(it.links.patch.small.toString())
                                    }
                                }
                            }
                        }

                        Status.ERROR -> {
                            Log.e(TAG, it.message!!)
                        }
                        Status.LOADİNG -> Log.e(TAG, "Loading")
                    }
                }
            }
        }
    }

    private fun backButton(){
        binding.btnBack.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}