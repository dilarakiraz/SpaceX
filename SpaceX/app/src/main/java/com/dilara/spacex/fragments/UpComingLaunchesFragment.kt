package com.dilara.spacex.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dilara.spacex.R
import com.dilara.spacex.adapter.UpcomingLaunchesAdapter
import com.dilara.spacex.databinding.FragmentUpComingLaunchesBinding
import com.dilara.spacex.util.Status
import com.dilara.spacex.util.Utils
import com.dilara.spacex.viewmodel.UpcomingLaunchesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpComingLaunchesFragment : Fragment(R.layout.fragment_up_coming_launches) {

    private var _binding:FragmentUpComingLaunchesBinding?=null
    private val binding get()=_binding!!
    private val viewModel : UpcomingLaunchesViewModel by viewModels()
    val TAG="UpcomingLaunches"
    lateinit var launchesAdapter: UpcomingLaunchesAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpComingLaunchesBinding.bind(view)

        setupRecyclerView()
        initObservers()
        initOnClick()
    }

    private fun setupRecyclerView(){
        launchesAdapter= UpcomingLaunchesAdapter()
        binding.recyclerViewLaunches.apply {
            adapter=launchesAdapter
            layoutManager=LinearLayoutManager(activity)
        }
    }

    private fun initObservers(){
        viewModel.getUpcomingLaunches().observe(viewLifecycleOwner){
            it?.let {resource->
                when(resource.status){
                    Status.SUCCESS ->{
                        resource.data?.let {launches->
                            launchesAdapter.different.submitList(launches)
                        }
                    }
                    Status.ERROR->Log.e(TAG,it.message!!)
                    Status.LOADİNG->Log.e(TAG,"Loading")
                }
            } ?: Utils.showToast(requireContext() ,"Roketler getirilemedi")
        }
    }

    private fun initOnClick(){
        launchesAdapter.setOnItemClickListener {
            findNavController().navigate(
                UpComingLaunchesFragmentDirections.actionUpComingLaunchesFragmentToLaunchDetailFragment(it)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}