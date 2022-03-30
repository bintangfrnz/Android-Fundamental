package com.bintangfajarianto.submission3.ui.home.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bintangfajarianto.submission3.data.local.entity.UserEntity
import com.bintangfajarianto.submission3.databinding.CardViewUserBinding
import com.bintangfajarianto.submission3.ui.detail.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

class FavoriteAdapter (private val listUsers: ArrayList<UserEntity>)
    : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {
    class ListViewHolder(binding: CardViewUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val userAvatar: CircleImageView = binding.userAvatar
        val userName: TextView = binding.userName
        val userUsername: TextView = binding.userUsername
        val userCompany: TextView = binding.userCompany
        val userLocation: TextView = binding.userLocation
        val toggleButton: ToggleButton = binding.toggleButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ListViewHolder{
        return ListViewHolder(
            CardViewUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with (holder) {
            val user = listUsers[position]
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .apply(RequestOptions().override(100,100))
                .into(userAvatar)
            userUsername.text = user.username
            userName.text = user.name
            userCompany.text = user.company
            userLocation.text = user.location

            toggleButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Toast.makeText(itemView.context, "Add $user.login", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(itemView.context, "Remove $user.login", Toast.LENGTH_SHORT).show()
                }
            }

            itemView.setOnClickListener {
                val userIntent = Intent(itemView.context, DetailActivity::class.java)
                userIntent.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                itemView.context.startActivity(userIntent)
            }
        }
    }

    override fun getItemCount(): Int = listUsers.size
}