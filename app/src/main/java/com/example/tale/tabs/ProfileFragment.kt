package com.example.tale.tabs

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.tale.R
import com.example.tale.misc.FollowerActivity
import com.example.tale.misc.SettingsActivity
import com.example.tale.model.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

var userDetails: UserDetails = UserDetails()

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()
        setHasOptionsMenu(true)
        CoroutineScope(IO).launch {
            updateName()

        }
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

        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        v.followingLayout.setOnClickListener {
            startActivity(Intent(v.context, FollowerActivity::class.java))

        }
        return v
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

    private suspend fun updateName() {
        //delay(1000)
        userDetails = FetchIndividual.fetchDetails(uid!!)
        updateUI(userDetails)
    }

    private suspend fun updateUI(userDetails: UserDetails?) {
        withContext(Main)
        {
            delay(500)
            println("Hello")
            println(userDetails?.getfirstName())
            if (userDetails?.getfirstName() != null)
                view?.textView?.text = userDetails.getfirstName()
            view?.following_count?.text = followersList.size.toString()

        }
    }
}

