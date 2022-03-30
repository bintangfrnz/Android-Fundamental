package com.bintangfajarianto.submission3.ui.home.favorite

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangfajarianto.submission3.R
import com.bintangfajarianto.submission3.data.local.entity.UserEntity
import com.bintangfajarianto.submission3.databinding.FragmentFavoriteBinding
import com.bintangfajarianto.submission3.utils.Constants

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val favViewModel: FavoriteViewModel by viewModels { factory }

        favViewModel.getFavoriteUsers().observe(viewLifecycleOwner) {
            val listUser = ArrayList<UserEntity>()
            for (user in it)
                listUser.add(user)

            if (listUser.size == 0) {
                val errorMsg = "No Favorite"
                binding.errorMessage.text = errorMsg
            } else {
                binding.errorMessage.text = Constants.BLANK
            }

            setRecyclerView(listUser)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerView(listUser: ArrayList<UserEntity>) {
        val rvUsers = binding.rvUsers

        // Add divider between item in recyclerview
        val div = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        div.setDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white)))
        rvUsers.addItemDecoration(div)

        rvUsers.layoutManager = LinearLayoutManager(context)
        rvUsers.adapter = FavoriteAdapter(listUser)
    }
}