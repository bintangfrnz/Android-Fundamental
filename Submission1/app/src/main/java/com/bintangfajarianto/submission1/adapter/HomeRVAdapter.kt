package com.bintangfajarianto.submission1.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bintangfajarianto.submission1.ui.DetailUserActivity
import com.bintangfajarianto.submission1.databinding.CardViewUserBinding
import com.bintangfajarianto.submission1.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

class HomeRVAdapter(private val listUsers: ArrayList<User>) : RecyclerView.Adapter<HomeRVAdapter.ListViewHolder>() {
    class ListViewHolder(binding: CardViewUserBinding) : RecyclerView.ViewHolder(binding.root) {
        var userAvatar: CircleImageView = binding.userAvatar
        var userName: TextView = binding.userName
        var userUsername: TextView = binding.userUsername
        var userCompany: TextView = binding.userCompany
        var userLocation: TextView = binding.userLocation
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardViewUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with (holder) {
            val user = listUsers[position]
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions().override(100,100))
                .into(userAvatar)
            userName.text = user.name
            userUsername.text = user.username
            userCompany.text = user.company
            userLocation.text = user.location

            itemView.setOnClickListener {
                val userIntent = Intent(itemView.context, DetailUserActivity::class.java)
                userIntent.putExtra(DetailUserActivity.EXTRA_USER, user)
                itemView.context.startActivity(userIntent)
            }
        }
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }
}