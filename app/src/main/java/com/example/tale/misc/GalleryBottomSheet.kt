package com.example.tale.misc

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tale.R
import com.example.tale.adapters.GalleryAdapter
import com.example.tale.viewModel.GalleryViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.gallery_bottom_sheet.view.*

class GalleryBottomSheet :BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.gallery_bottom_sheet,container,false)


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        return dialog
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[GalleryViewModel::class.java]
        view.galleryBottomSheetRecycler.layoutManager= GridLayoutManager(view.context,4)
        viewModel.loadPath(view.context)
        viewModel.imagePath.observe(this) {
            view.galleryBottomSheetRecycler?.adapter = GalleryAdapter(view.context, it,object : GalleryAdapter.OnItemClickListener{
                override fun onItemClick(position: Int, path: String) {
                    startActivity(Intent(context,PostPreviewActivity::class.java).putExtra("path",path))
                }

            })
    }
    }



}