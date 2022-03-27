package com.bintangfajarianto.submission2.ui.detail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.bintangfajarianto.submission2.databinding.FragmentFollowingBinding
import com.bintangfajarianto.submission2.model.User

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.listFollowing.observe(viewLifecycleOwner) {
            val listUser = ArrayList<User>()
            for (user in it)
                listUser.add(user)

            showRecyclerView(listUser)
        }

        detailViewModel.isLoadingFragment.observe(viewLifecycleOwner) {
            setLoading(it)
        }

        detailViewModel.messageErrorFollowing.observe(viewLifecycleOwner) {
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

    private fun setLoading(isLoading: Boolean) {
        binding.progressBarFragment.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}