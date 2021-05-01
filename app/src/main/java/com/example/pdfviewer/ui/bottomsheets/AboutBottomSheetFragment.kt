package com.example.pdfviewer.ui.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pdfviewer.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AboutBottomSheetFragment:BottomSheetDialogFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.about_bottom_sheet_layout, container, false)

    }
}