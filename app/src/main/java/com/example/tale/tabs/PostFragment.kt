package com.example.tale.tabs


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tale.misc.PostPreviewActivity
import com.google.android.material.tabs.TabLayout
import com.example.tale.R


class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val postView= inflater.inflate(R.layout.fragment_post, container, false)

        val cardView = postView.findViewById<com.google.android.material.card.MaterialCardView>(R.id.pic_select)
        cardView.setOnClickListener{
           val intent= Intent(context, PostPreviewActivity::class.java)
            startActivity(intent)
        }

        return  postView

    }




    override fun onResume() {
        super.onResume()
       //(activity as AppCompatActivity).viewPager.setPageTransformer(ZoomOutPageTransformer(
         //  (activity as AppCompatActivity).tabLayout,
           //(activity as AppCompatActivity).toolbar,
           //(activity as AppCompatActivity).viewPager
        //))
        (activity as AppCompatActivity).findViewById<TabLayout>(R.id.tabLayout).visibility = View.GONE
     //  val clayout=(activity as AppCompatActivity).findViewById<ViewGroup>(R.id.viewPager)
      // clayout?.layoutTransition?.enableTransitionType(
        //    LayoutTransition.CHANGING)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()


    }

}