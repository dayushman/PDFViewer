package com.example.pdfviewer.selection

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfviewer.adaptor.PDFAdaptor

class MyItemDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x,event.y)

        if (view!=null){
            val holder = recyclerView.getChildViewHolder(view)
            if (holder is PDFAdaptor.PDFViewHolder)
                return ( holder.getItemDetails())
        }
        return null
    }
}