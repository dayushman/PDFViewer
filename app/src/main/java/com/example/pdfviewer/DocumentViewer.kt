package com.example.pdfviewer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_document_viewer.*
import timber.log.Timber
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