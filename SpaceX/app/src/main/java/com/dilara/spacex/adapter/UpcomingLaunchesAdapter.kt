package com.dilara.spacex.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dilara.spacex.databinding.ItemUpcomingLaunchesBinding
import com.dilara.spacex.model.Rockets
import com.dilara.spacex.model.upcoming_launches.UpcomingLaunches

class UpcomingLaunchesAdapter :
    RecyclerView.Adapter<UpcomingLaunchesAdapter.LaunchesViewHolder>(){

    inner class LaunchesViewHolder(val binding: ItemUpcomingLaunchesBinding) :RecyclerView.ViewHolder(binding.root)

    private val callback= object : DiffUtil.ItemCallback<UpcomingLaunches>(){
        override fun areItemsTheSame(
            oldItem: UpcomingLaunches,
            newItem: UpcomingLaunches
        ): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UpcomingLaunches,
            newItem: UpcomingLaunches
        ): Boolean {
            return oldItem==newItem
        }

    }
    val different= AsyncListDiffer(this,callback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesViewHolder =
        LaunchesViewHolder(
        ItemUpcomingLaunchesBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
    )

    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) {
        val launches=different.currentList[position]

        holder.binding.apply {
            textFlightNumber.text=launches.flightNumber.toString()
            textName.text=launches.name
            textDate.text=launches.date
            holder.itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(launches) }
            }
        }
    }

    override fun getItemCount(): Int =different.currentList.size

    private var onItemClickListener:((UpcomingLaunches)->Unit)?=null

    fun setOnItemClickListener(listener:(UpcomingLaunches)->Unit){
        onItemClickListener=listener
    }
}