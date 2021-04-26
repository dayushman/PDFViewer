package com.example.pdfviewer.selection

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfviewer.adaptor.PDFAdaptor

class MyItemDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<String>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<String>? {
        val view = recyclerView.findChildViewUnder(event.x,event.y)

        if (view!=null)
            return (recyclerView.getChildViewHolder(view) as PDFAdaptor.PDFViewHolder).getItemDetails()
        return null
    }
}