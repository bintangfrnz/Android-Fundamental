package com.bintangfajarianto.submission2.ui.detail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangfajarianto.submission2.R
import com.bintangfajarianto.submission2.adapter.UserAdapter
import com.bintangfajarianto.submission2.databinding.FragmentUserConnectionBinding
import com.bintangfajarianto.submission2.model.User

class UserConnectionFragment : Fragment() {

    private var _binding: FragmentUserConnectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserConnectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(SECTION_NUMBER, 0)
        val listUser = arguments?.getParcelableArrayList<User>(LIST_USER)

        val fragText = binding.fragmentText
        fragText.text = TAB_FRAGMENT[(index ?: 1) -1]

        val rvUsers = binding.rvUsers

        // Add divider between item in recyclerview
        val div = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        div.setDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white)))
        rvUsers.addItemDecoration(div)

        rvUsers.layoutManager = LinearLayoutManager(context)
        val listUserAdapter = UserAdapter(listUser as ArrayList<User>)
        rvUsers.adapter = listUserAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val SECTION_NUMBER = "section_number"
        const val LIST_USER = "list_user"
        private val TAB_FRAGMENT = arrayOf(
            "Follower",
            "Following"
        )
    }
}