package com.example.pdfviewer.selection

import androidx.recyclerview.selection.ItemKeyProvider
import com.example.pdfviewer.adaptor.PDFAdaptor

class MyItemKeyProvider(private val adapter: PDFAdaptor) : ItemKeyProvider<Long>(SCOPE_CACHED)
{
    override fun getKey(position: Int): Long =
            position.toLong()
    override fun getPosition(key: Long): Int =
            adapter.pdfFiles.indexOfFirst{
                it.absolutePath == adapter.pdfFiles[key.toInt()].absolutePath
            }
}