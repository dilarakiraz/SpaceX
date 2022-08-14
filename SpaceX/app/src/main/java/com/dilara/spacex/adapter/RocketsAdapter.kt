package com.dilara.spacex.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dilara.spacex.R
import com.dilara.spacex.databinding.ItemRocketsBinding
import com.dilara.spacex.model.FavoriClickInterface
import com.dilara.spacex.model.Rockets
import com.dilara.spacex.util.downloadImage


class RocketsAdapter(private val favoriClick: FavoriClickInterface) :
    RecyclerView.Adapter<RocketsAdapter.RocketsViewHolder>(){

    inner class RocketsViewHolder(val binding:ItemRocketsBinding):RecyclerView.ViewHolder(binding.root)

    private val callback=object :DiffUtil.ItemCallback<Rockets>(){
        override fun areItemsTheSame(oldItem: Rockets, newItem: Rockets): Boolean {
            return oldItem.id==newItem.id
        }
        override fun areContentsTheSame(oldItem: Rockets, newItem: Rockets): Boolean {
            return oldItem==newItem
        }
    }

    val different=AsyncListDiffer(this,callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsViewHolder=RocketsViewHolder(
        ItemRocketsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
    )

    override fun onBindViewHolder(holder: RocketsViewHolder, position: Int) {
        val rockets = different.currentList[position]


        holder.binding.run {
            textName.text = rockets.name
            imgRocket.downloadImage(rockets.flickrImages[0])
            if(rockets.isLike){
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.ic_baseline_star_24
                    )
                )
            }else{
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.ic_baseline_star_outline_24)
                )
            }
            btnFavorite.setOnClickListener {
                favoriClick.onClickFavorite(rockets)
            }
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(rockets) }
            }
        }
    }

    fun updateRockets(rockets: Rockets){
        val index=different.currentList.indexOfFirst {
            rockets.id ==it.id
        }
        notifyItemChanged(index)
    }

    fun deleteRocket(id:String){
        val index=different.currentList.indexOfFirst {
            id == it.id
        }
        notifyItemRemoved(index)
    }

    override fun getItemCount(): Int  = different.currentList.size

    private var onItemClickListener: ((Rockets) -> Unit)? = null

    fun setOnItemClickListener(listener: (Rockets) -> Unit) {
            onItemClickListener = listener
        }
    }