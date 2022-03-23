package com.bintangfajarianto.submission2.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bintangfajarianto.submission2.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
//    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME)



//        val layoutManager = LinearLayoutManager(this)
//        binding.rvUsers.layoutManager = layoutManager
//        binding.rvUsers.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

    }

//    private fun setRecyclerView(listUser: List<User>) {
//        val list = ArrayList<User>()
//        for (user in listUser) {
//            list.add(user)
//        }
//
//        binding.rvUsers.adapter = SearchUserAdapter(list)
//        binding.textInputEditText.setText(Constants.BLANK)
//        binding.textInputEditText.clearFocus()
//    }
//
//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }

    companion object {
        const val USERNAME = "username"
    }
}