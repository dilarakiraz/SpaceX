package com.dilara.spacex.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dilara.spacex.R
import com.dilara.spacex.databinding.FragmentRocketDetailBinding
import com.dilara.spacex.util.Status
import com.dilara.spacex.util.downloadImageForCarousel
import com.dilara.spacex.view.MainActivity
import com.dilara.spacex.viewmodel.RocketsDetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RocketDetailFragment : Fragment(R.layout.fragment_rocket_detail) {
    private var _binding:FragmentRocketDetailBinding?=null
    private val binding get() =_binding!!
    private val viewModel:RocketsDetailViewModel by viewModels()
    val args:RocketDetailFragmentArgs by navArgs()
    val TAG="RocketDetailFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRocketDetailBinding.bind(view)

        initObservers()
        backButton()
        initOnClick()
        setDrawable()
    }
    private fun initOnClick(){
        binding.btnFavorite.setOnClickListener{
            args.rocketId.id?.let { it1 ->
                viewModel.controlFav(it1).observe(viewLifecycleOwner){
                    if(it.data==true){
                        viewModel.deleteId(args.rocketId)
                        binding.btnFavorite.setImageDrawable(
                            view?.let { it1->
                                ContextCompat.getDrawable(
                                    it1.context,
                                    R.drawable.ic_baseline_star_24
                                )
                            }
                        )
                        view?.let { it1-> Snackbar.make(it1,"SİLİNDİ",Snackbar.LENGTH_LONG).show() }
                    }else{
                        viewModel.upsert(args.rocketId)
                        binding.btnFavorite.setImageDrawable(
                            view?.let { it1->
                                ContextCompat.getDrawable(
                                    it1.context,
                                    R.drawable.ic_baseline_star_24
                                )
                            }
                        )
                        view?.let { it1-> Snackbar.make(it1,"Kaydedildi",Snackbar.LENGTH_LONG).show()}
                    }
                }
            }
        }
    }

    private fun initObservers(){
        args.rocketId?.let {
            it.id?.let { it1 ->
                viewModel.getRocketDetail(it1).observe(viewLifecycleOwner) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data?.let {
                                    binding.apply {
                                        textName.text = it.name
                                        textDesciption.text = it.description
                                        carousel(it.flickrImages)
                                    }
                                }
                            }
                            Status.ERROR -> {
                                Log.e(TAG, it.message!!)
                            }
                            Status.LOADİNG-> Log.e(TAG, "Loading")
                        }
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
    private fun carousel(images:List<String>){
            binding.carouselView.apply {
                size=images.size
                resource=R.layout.carousel_home_item
                autoPlay=true
                indicatorAnimationType=IndicatorAnimationType.SCALE
                carouselOffset=OffsetType.CENTER
                setCarouselViewListener { view, position ->
                    val imageView=view.findViewById<ImageView>(R.id.imgCarousel)
                    var imgPath:String?=images[position]
                    if(imgPath.isNullOrEmpty())
                        imgPath=""
                    imageView.downloadImageForCarousel(imgPath)
                }
                show()
            }
    }
    override fun onDestroy(){
            super.onDestroy()
            _binding=null
        }
    private fun setDrawable(){
        if(args.rocketId.isLike){
            binding.btnFavorite.setImageDrawable(
                view?.let {
                    ContextCompat.getDrawable(
                        it.context,
                        R.drawable.ic_baseline_star_24
                    )
                }
            )
        }else{
            binding.btnFavorite.setImageDrawable(
                view?.let {
                    ContextCompat.getDrawable(
                        it.context,
                        R.drawable.ic_baseline_star_outline_24
                    )
                }
            )
        }
    }
}
