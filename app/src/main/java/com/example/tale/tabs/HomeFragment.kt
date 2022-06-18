package com.example.tale.tabs


import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tale.R
import com.example.tale.adapters.StoryListAdapter
import com.example.tale.model.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var isFirst: Boolean = true

class HomeFragment : Fragment() {
    private var isSearch = false
    private lateinit var adapter: StoryListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.show()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = view?.findViewById<RecyclerView>(R.id.story_recycler)
        recyclerView?.layoutManager = GridLayoutManager(context, 4)
        adapter = StoryListAdapter(activity, mapp)
        recyclerView?.adapter = adapter

        CoroutineScope(IO).launch {
            updateData()
        }


        iniRefreshListener(view)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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
                    item.icon = myDrawable
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
                    item.icon = myDrawable
                    isSearch = false
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
        if (menu.findItem(R.id.settings) != null) {
            menu.findItem(R.id.settings).isVisible = false
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity).findViewById<TabLayout>(R.id.tabLayout).isVisible = true
        //  (activity as AppCompatActivity).viewPager.setPageTransformer(ZoomOutPageTransformer(
        //  (activity as AppCompatActivity).tabLayout,
        //  (activity as AppCompatActivity).toolbar,
        //    (activity as AppCompatActivity).viewPager
        //))
        (activity as AppCompatActivity).viewPager.setPageTransformer(null)

    }


    //Background fetch
    private suspend fun updateData() {
        println("Thread Name: ${Thread.currentThread().name}")
        FollowCheck.followers()
        updateAdapter()

    }

    private suspend fun updateAdapter() {
        withContext(Main)
        {
            delay(500)

            adapter.notifyDataSetChanged()
        }
    }

    private fun iniRefreshListener(view: View) {
        view.refreshLayout.setOnRefreshListener { // This method gets called when user pull for refresh,
            // You can make your API call here,
            CoroutineScope(IO).launch {
                updateData()
            }
            val handler = Handler()
            handler.postDelayed({
                if (view.refreshLayout.isRefreshing) {
                    view.refreshLayout.isRefreshing = false
                }
            }, 3000)
        }
    }

}


