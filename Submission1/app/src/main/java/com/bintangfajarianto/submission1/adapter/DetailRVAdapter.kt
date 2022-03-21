package com.bintangfajarianto.submission1.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bintangfajarianto.submission1.ui.DetailUserActivity
import com.bintangfajarianto.submission1.databinding.CardViewUserHorizontalBinding
import com.bintangfajarianto.submission1.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

class DetailRVAdapter(private val listUsers: ArrayList<User>, private val activity: Activity) : RecyclerView.Adapter<DetailRVAdapter.ListViewHolder>() {
    class ListViewHolder(binding: CardViewUserHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        var userAvatar: CircleImageView = binding.userAvatar
        var userName: TextView = binding.userName
        var userUsername: TextView = binding.userUsername
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardViewUserHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

            itemView.setOnClickListener {
                val userIntent = Intent(itemView.context, DetailUserActivity::class.java)
                userIntent.putExtra(DetailUserActivity.EXTRA_USER, user)
                itemView.context.startActivity(userIntent)
                activity.finish()
            }
        }
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }
}