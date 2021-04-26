package com.example.pdfviewer.selection

import androidx.recyclerview.selection.ItemKeyProvider
import com.example.pdfviewer.adaptor.PDFAdaptor

class MyItemKeyProvider(private val adapter: PDFAdaptor) : ItemKeyProvider<String>(SCOPE_CACHED)
{
    override fun getKey(position: Int): String? =
            adapter.pdfFiles[position].file.absolutePath
    override fun getPosition(key: String): Int =
            adapter.pdfFiles.indexOfFirst {it.file.absolutePath == key}
}