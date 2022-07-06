package com.example.tale.tabs


import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tale.adapters.StoryListAdapter

import com.google.android.material.tabs.TabLayout
import com.example.tale.R
import com.example.tale.model.StoryDetails
import com.example.tale.model.UserDetails
import com.example.tale.viewModel.HomeFragmentViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.stream.Stream
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private var isSearch = false
    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var adapter: StoryListAdapter
    private lateinit var viewModel:HomeFragmentViewModel
    private var i = 3
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.show()
        // Inflate the layout for this fragment
        val homeView = inflater.inflate(R.layout.fragment_home, container, false)
        //(homeView.story_recycler.width)
        val recyclerView = homeView?.findViewById<RecyclerView>(R.id.story_recycler)


        //ViewModel for updating recycler data
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]

        viewModel.fetchStory()

        viewModel.span.observe(viewLifecycleOwner){ it ->

            recyclerView?.layoutManager = GridLayoutManager(homeView.context, it)
            viewModel.testData.observe(viewLifecycleOwner) {itt->
                if(itt.size!=0) {
                    println(itt.size)
                    val sorted = itt.toList().sortedByDescending { itttm ->
                         itttm.second.last().gettime()
                    }.toMap()
                    println(sorted)
                    adapter = StoryListAdapter(
                        homeView.context,
                        sorted, it
                    )
                    recyclerView?.adapter = adapter
                    adapter.notifyItemInserted(0)
                }
            }

        }
        viewModel.updateRecyclerSpan(i)
        recyclerView?.setOnDragListener { view, dragEvent ->

            when (dragEvent.action) {

                MotionEvent.ACTION_DOWN -> {
                    val x = dragEvent.x
                    val y = dragEvent.y
                   // println("X:$x Y:$y")
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    val x = dragEvent.x
                    val y = dragEvent.y
                   // println("Pointer_X:$x pointer_Y:$y")
                }
            }


            view.performClick()
            true
        }
        //Pull to refresh
        iniRefreshListener(homeView)
        permissionFileCheck()

        return homeView
    }


    private fun permissionFileCheck() {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // postView.permissionInfo.visibility= View.GONE
                    //startCamera(postView)
                } else {
                    //postView.permissionInfo.visibility= View.VISIBLE

                }
            }

        when {
            ContextCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                //postView.permissionInfo.visibility= View.GONE
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                requestPermissionLauncher.launch(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity).findViewById<TabLayout>(R.id.tabLayout).isVisible = true


    }


    private fun iniRefreshListener(view: View) {

        view.refreshLayout.setOnRefreshListener { // This method gets called when user pull for refresh,
            // You can make your API call here,
            //adapter.notifyDataSetChanged()
            val handler = Handler()
            handler.postDelayed({
                if (view.refreshLayout.isRefreshing) {
                    view.refreshLayout.isRefreshing = false
                }
            }, 3000)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.profmenu, menu)
                (activity as AppCompatActivity).supportActionBar?.title = "Home"
                if (menu.findItem(R.id.settings) != null) {
                    menu.findItem(R.id.settings).isVisible = false
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.toggle ->{
                        if(i<4)
                        {
                            i++
                            viewModel.updateRecyclerSpan(i)

                        }
                        else
                        {
                            i=1
                            viewModel.updateRecyclerSpan(i)
                        }

                        true
                    }
                    R.id.search -> {
                        if (!isSearch) {
                            isSearch = true
                            (activity as AppCompatActivity).searchBox.setSelectAllOnFocus(true)
                            (activity as AppCompatActivity).searchBox.visibility = View.VISIBLE
                            val myDrawable =
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.exit,
                                    context?.theme
                                ) // The ID of your drawable.
                            (activity as AppCompatActivity).toolbar.title = ""
                            menuItem.icon = myDrawable
                            FlingAnimation(
                                (activity as AppCompatActivity).searchBox,
                                DynamicAnimation.TRANSLATION_X
                            ).apply {
                                setStartVelocity(0f)
                                setMinValue(0f)
                                setMaxValue(1f)
                                friction = 1.1f
                                start()
                            }
                            ObjectAnimator.ofFloat(
                                (activity as AppCompatActivity).searchBox,
                                "scaleX",
                                1f
                            ).apply {
                                duration = 200
                                startDelay = 300
                                start()
                            }
                        } else {
                            ObjectAnimator.ofFloat(
                                (activity as AppCompatActivity).searchBox,
                                "scaleX",
                                0f
                            ).apply {
                                duration = 500
                                start()
                            }
                            (activity as AppCompatActivity).searchBox.visibility = View.GONE
                            val myDrawable =
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.ic_baseline_search_24,
                                    context?.theme
                                ) // The ID of your drawable.
                            (activity as AppCompatActivity).toolbar.title = "Home"
                            menuItem.icon = myDrawable
                            isSearch = false
                        }
                        true
                    }
                    else -> return false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}


