package com.example.pdfviewer.ui.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pdfviewer.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.info_bottom_sheet_layout.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow
import kotlin.math.round


class InfoBottomSheetFragment(private val file: File):BottomSheetDialogFragment() {




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(file?.lastModified()
                ?: 0 * 1000))
        var size = file.length().div(1.0f * 1024 * 1024)
        val scale = 10.0.pow(2.0)
        size = (round(size * scale) / scale).toFloat()
        tv_file_size.text = "$size MB"
        tv_file_loc.text = file.absolutePath
        tv_file_name.text = file.name
        tv_date.text = date

    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.info_bottom_sheet_layout, container, false)

    }

}

