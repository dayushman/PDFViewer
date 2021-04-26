package com.example.pdfviewer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pdfviewer.R
import kotlinx.android.synthetic.main.activity_document_viewer.*
import java.io.File

class DocumentViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_viewer)
        val path = intent.getStringExtra("path")

        pdfView.fromFile(File(path)).load()
        supportActionBar?.hide()
    }
}