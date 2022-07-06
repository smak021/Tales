package com.example.tale.tabs

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tale.CollapseTestActivity2
import com.example.tale.misc.FollowingActivity
import com.example.tale.misc.SettingsActivity
import com.example.tale.model.*
import com.google.android.material.tabs.TabLayout
import com.example.tale.R
import com.example.tale.adapters.ProfileTaleAdapter
import com.example.tale.misc.FriendsDiscoveryActivity
import com.example.tale.viewModel.ProfileFragmentViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

var userDetails: UserDetails = UserDetails()

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val profileView = inflater.inflate(R.layout.fragment_profile, container, false)
        val button = profileView.findViewById<AppCompatButton>(R.id.addFriendsButton)
        button.setOnTouchListener(this)
        val recycler = profileView.findViewById<RecyclerView>(R.id.talesRecyclerview)
        recycler.layoutManager = GridLayoutManager(profileView.context,3)
        val viewModel = ViewModelProvider(this)[ProfileFragmentViewModel::class.java]
        viewModel.fetchUserData()
        viewModel.testData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                profileView.textView.text = it[0].getfull_name().toString()
                if(it[0].getuserPic()!=null){
                Glide.with(this).load(it[0].getuserPic()).into(profileImage)}
            }
        }

        viewModel.fetchFollowersList()
        viewModel.followersLiveData.observe(viewLifecycleOwner){
            if(it.isNotEmpty())
            {
                profileView.followers_count.text = it.size.toString()

            }else
            {
                profileView.followers_count.text = "0"
            }

        }

        viewModel.fetchFollowingList()
        followersL.observe(viewLifecycleOwner) {
            println(it.size.toString())
            if(it.isNotEmpty()) {
                profileView.following_count.text = it.size.toString()
            }
            else{
                profileView.following_count.text = "0"
            }
        }
        viewModel.fetchUserStory()
        viewModel.storyData.observe(viewLifecycleOwner){
            profileView.story_count.text = it.size.toString()
            recycler.adapter= ProfileTaleAdapter(context,it)
            if(it.isEmpty())
            {
                recycler.visibility = View.GONE
                profileView.noresultLT.visibility = View.VISIBLE
                profileView.info.visibility = View.VISIBLE
            }
            else
            {
                recycler.visibility = View.VISIBLE
                profileView.noresultLT.visibility = View.GONE
                profileView.info.visibility = View.GONE
            }
        }

        profileView.followingLayout.setOnClickListener {
            startActivity(Intent(profileView.context, FollowingActivity::class.java))
        }

        profileView.followersLayout.setOnClickListener {
            startActivity(Intent(profileView.context, CollapseTestActivity2::class.java))
        }

        return profileView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.profmenu, menu)
                (activity as AppCompatActivity).supportActionBar?.title = "Profile"
                if (menu.findItem(R.id.toggle) != null) {
                    menu.findItem(R.id.toggle).isVisible = false
                }
                if (menu.findItem(R.id.search) != null) {
                    menu.findItem(R.id.search).isVisible = false
                }
                (activity as AppCompatActivity).searchBox.visibility = View.GONE
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.settings -> {
                        startActivity(Intent(context, SettingsActivity::class.java))
                        true
                    }
                    else -> return false
                }
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).title = "Profile"




    }

    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity).findViewById<TabLayout>(R.id.tabLayout).isVisible = true
    }


}

private fun AppCompatButton.setOnTouchListener(profileFragment: ProfileFragment) {

    setOnClickListener {
        profileFragment.context?.startActivity(Intent(profileFragment.context,FriendsDiscoveryActivity::class.java))

    }

}


