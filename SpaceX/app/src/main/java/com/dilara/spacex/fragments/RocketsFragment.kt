package com.dilara.spacex.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dilara.spacex.R
import com.dilara.spacex.adapter.RocketsAdapter
import com.dilara.spacex.databinding.FragmentRocketsBinding
import com.dilara.spacex.model.FavoriClickInterface
import com.dilara.spacex.model.Rockets
import com.dilara.spacex.util.Status
import com.dilara.spacex.viewmodel.RocketsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RocketsFragment : Fragment(R.layout.fragment_rockets), FavoriClickInterface {

    private var _binding:FragmentRocketsBinding?=null
    private val binding get() = _binding!!
    private val viewModel:RocketsViewModel by viewModels()
    lateinit var rocketsAdapter: RocketsAdapter
    val TAG="RocketsFragment"

    override fun onViewCreated(view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view,savedInstanceState)
        _binding= FragmentRocketsBinding.bind(view)

        setupRecyclerView()
        viewModel.getRockets()
        setupRecyclerView()
        initOnClick()
        initObservers()
    }


    private fun controlFav(rockets: Rockets){
        rockets.id?.let {
            viewModel.controlFav(it).observe(viewLifecycleOwner){
                if(it.data==true){
                    viewModel.deleteId(rockets.id,rockets)
                    rocketsAdapter.updateRockets(rockets)
                    Toast.makeText(requireContext(),"Silindi",Toast.LENGTH_LONG).show()
                }else{
                    viewModel.upsert(rockets)
                    rocketsAdapter.updateRockets(rockets)
                    Toast.makeText(requireContext(),"Kaydedildi",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initOnClick(){
        rocketsAdapter.setOnItemClickListener {
            findNavController().navigate(
                RocketsFragmentDirections.actionRocketsFragmentToRocketDetailFragment(it)
            )
        }
    }

    private fun initObservers(){
        viewModel.getRockets().observe(viewLifecycleOwner) {
            it.let{resource ->
                when(resource.status){
                    Status.SUCCESS->{
                        resource.data?.let { response->
                            getFavorites(response)
                        }
                    }
                    Status.ERROR->{
                        Log.e(TAG,it.message!!)
                    }
                    Status.LOADİNG->{
                        Log.e(TAG,"Loading")
                    }
                }
            }
        }
    }

    private fun getFavorites(rockets:List<Rockets>){
        var saveList:List<Rockets>

        viewModel.saveRockets().observe(viewLifecycleOwner){
            saveList = it
            saveList.forEach{ favRocket->
                rockets.find { rockets->
                    rockets.id==favRocket.id
                }?.also {
                    it.isLike=true
                }
            }
            rocketsAdapter.different.submitList(rockets)
        }
    }

    private fun setupRecyclerView(){
        rocketsAdapter= RocketsAdapter(this)

        binding.recyclerViewRockets.apply {
            adapter=rocketsAdapter
            layoutManager=LinearLayoutManager(activity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null

    }
    override fun onClickFavorite(rockets: Rockets) {
        controlFav(rockets)
    }

}





