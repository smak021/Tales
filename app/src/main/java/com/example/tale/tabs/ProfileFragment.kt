package com.example.tale.tabs

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tale.misc.FollowerActivity
import com.example.tale.misc.SettingsActivity
import com.example.tale.model.*
import com.google.android.material.tabs.TabLayout
import com.example.tale.R
import com.example.tale.viewModel.ProfileFragmentViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

var userDetails: UserDetails = UserDetails()

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(context, SettingsActivity::class.java))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        (activity as AppCompatActivity).supportActionBar?.title = "Profile"
        if (menu.findItem(R.id.search) != null) {
            menu.findItem(R.id.search).isVisible = false
        }
        (activity as AppCompatActivity).searchBox.visibility = View.GONE

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val profileView = inflater.inflate(R.layout.fragment_profile, container, false)

        val viewModel = ViewModelProvider(this)[ProfileFragmentViewModel::class.java]
        viewModel.fetchUserData()
        viewModel.testData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {

                profileView.textView.text = it[0].getfull_name().toString()
            }
        }
        viewModel.fetchFollowersList()
        viewModel.followers.observe(viewLifecycleOwner, Observer {
            println(it.size.toString())
            profileView.following_count.text = it.size.toString()
        })

        profileView.followingLayout.setOnClickListener {
            startActivity(Intent(profileView.context, FollowerActivity::class.java))

        }
        return profileView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).title = "Profile"
    }

    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity).findViewById<TabLayout>(R.id.tabLayout).isVisible = true
        (activity as AppCompatActivity).viewPager.setPageTransformer(null)
    }


}

