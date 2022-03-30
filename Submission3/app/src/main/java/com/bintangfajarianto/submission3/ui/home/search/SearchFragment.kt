package com.bintangfajarianto.submission3.ui.home.search

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangfajarianto.submission3.R
import com.bintangfajarianto.submission3.data.remote.response.User
import com.bintangfajarianto.submission3.databinding.FragmentSearchBinding
import com.bintangfajarianto.submission3.ui.home.UserAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Customize SearchView Color
        val searchView = binding.searchView
        val searchIcon: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon)
        val searchHintIcon: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_voice_btn)
        val closeIcon: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        val queryText: TextView = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue_light))
        searchHintIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue_light))
        closeIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue_light))
        queryText.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_light))

        // Set up SearchView
        binding.searchView.setOnQueryTextListener( object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.toString().isEmpty()) {
                    searchViewModel.reset()
                } else {
                    searchViewModel.getUsersByUsername(query.toString())
                }
                return true
            }
        })

        searchViewModel.listUser.observe(viewLifecycleOwner) {
            val listUser = ArrayList<User>()
            for (user in it)
                listUser.add(user)

            setRecyclerView(listUser)
        }

        searchViewModel.messageError.observe(viewLifecycleOwner) {
            binding.errorMessage.text = it
        }

        searchViewModel.isLoading.observe(viewLifecycleOwner) {
            setLoading(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setRecyclerView(listUser: ArrayList<User>) {
        val rvUsers = binding.rvUsers

        // Add divider between item in recyclerview
        val div = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        div.setDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white)))
        rvUsers.addItemDecoration(div)

        rvUsers.layoutManager = LinearLayoutManager(context)
        rvUsers.adapter = UserAdapter(listUser)
    }
}