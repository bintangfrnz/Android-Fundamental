package com.bintangfajarianto.submission2.ui.detail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangfajarianto.submission2.R
import com.bintangfajarianto.submission2.adapter.UserAdapter
import com.bintangfajarianto.submission2.databinding.FragmentUserConnectionBinding
import com.bintangfajarianto.submission2.model.User
import com.bintangfajarianto.submission2.utils.Constants

class UserConnectionFragment : Fragment() {

    private var _binding: FragmentUserConnectionBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel by activityViewModels<DetailViewModel>()
    var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserConnectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.listFollow.observe(viewLifecycleOwner) {
            val listUser = ArrayList<User>()
            for (user in it) {
                listUser.add(user)
            }
            showRecyclerView(listUser)
        }

        detailViewModel.messageError.observe(viewLifecycleOwner) {
            binding.errorMessage.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showRecyclerView(listUser: ArrayList<User>) {
        val rvUsers = binding.rvUsers

        // Add divider between item in recyclerview
        val div = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        div.setDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white)))
        rvUsers.addItemDecoration(div)

        rvUsers.layoutManager = LinearLayoutManager(requireActivity())
        rvUsers.adapter = UserAdapter(listUser)
    }
}