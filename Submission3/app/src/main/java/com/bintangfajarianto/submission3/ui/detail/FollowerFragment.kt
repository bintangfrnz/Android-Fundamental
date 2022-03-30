package com.bintangfajarianto.submission3.ui.detail

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
import com.bintangfajarianto.submission3.R
import com.bintangfajarianto.submission3.data.remote.response.User
import com.bintangfajarianto.submission3.databinding.FragmentFollowerBinding
import com.bintangfajarianto.submission3.ui.home.UserAdapter

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.listFollower.observe(viewLifecycleOwner) {
            val listUser = ArrayList<User>()
            for (user in it)
                listUser.add(user)

            showRecyclerView(listUser)
        }

        detailViewModel.isLoadingFragment.observe(viewLifecycleOwner) {
            setLoading(it)
        }

        detailViewModel.messageErrorFollower.observe(viewLifecycleOwner) {
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