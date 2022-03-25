package com.bintangfajarianto.submission2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bintangfajarianto.submission2.databinding.CardViewSimpleBinding
import com.bintangfajarianto.submission2.model.User
import com.bintangfajarianto.submission2.ui.detail.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val listUsers: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    class ListViewHolder(binding: CardViewSimpleBinding) : RecyclerView.ViewHolder(binding.root) {
        var userAvatar: CircleImageView = binding.userAvatar
        var userUsername: TextView = binding.userUsername
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ListViewHolder{
        return ListViewHolder(
            CardViewSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with (holder) {
            val user = listUsers[position]
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .apply(RequestOptions().override(100,100))
                .into(userAvatar)
            userUsername.text = user.login

            itemView.setOnClickListener {
                val userIntent = Intent(itemView.context, DetailActivity::class.java)
                userIntent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                itemView.context.startActivity(userIntent)
            }
        }
    }

    override fun getItemCount(): Int = listUsers.size
}