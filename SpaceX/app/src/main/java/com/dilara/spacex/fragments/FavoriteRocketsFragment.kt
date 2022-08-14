package com.dilara.spacex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dilara.spacex.R
import com.dilara.spacex.adapter.RocketsAdapter
import com.dilara.spacex.databinding.FragmentFavoriteRocketsBinding
import com.dilara.spacex.model.FavoriClickInterface
import com.dilara.spacex.model.Rockets
import com.dilara.spacex.viewmodel.FavoriteRocketsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRocketsFragment : Fragment(R.layout.fragment_favorite_rockets) ,FavoriClickInterface {
    private var _binding:FragmentFavoriteRocketsBinding?=null
    private val binding get()= _binding!!
    private val viewModel:FavoriteRocketsViewModel by viewModels()
    lateinit var  rocketsAdapter:RocketsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentFavoriteRocketsBinding.bind(view)

        setupRecyclerView()
        initObservers()
        initNavigation()
    }

    private fun initObservers(){
        viewModel.getSavedRockets().observe(viewLifecycleOwner){
            rocketsAdapter.different.submitList(it.toList())

        }
    }

    private fun initNavigation(){
        rocketsAdapter.setOnItemClickListener {
            findNavController().navigate(
                FavoriteRocketsFragmentDirections.actionFavoriteRocketsFragmentToRocketDetailFragment(it)
            )
        }
    }

    private fun controlFav(rockets:Rockets){
        rockets.id?.let {it->
            viewModel.controlFav(it).observe(viewLifecycleOwner) {
                if(it.data==true){
                    viewModel.deleteRockets(rockets)
                    Toast.makeText(requireContext(),"SİLİNDİ",Toast.LENGTH_LONG).show()
                }else{
                    viewModel.upsert(rockets)
                    Toast.makeText(requireContext(),"Favorilere Eklendi",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onClickFavorite(rockets: Rockets) {
        controlFav(rockets)
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
}
