package com.example.pdfviewer.adaptor

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.marginEnd
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfviewer.modal.FileModal
import com.example.pdfviewer.R
import kotlinx.android.synthetic.main.doc_holder.view.*

class PDFAdaptor: RecyclerView.Adapter<PDFAdaptor.PDFViewHolder>() {
    init {
        setHasStableIds(true)
    }


    private var tracker:SelectionTracker<Long>? = null
    var pdfFiles: MutableList<FileModal>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)
    inner class PDFViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val image = itemView.ivPdf
        val text = itemView.tvPdfName
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
                object : ItemDetailsLookup.ItemDetails<Long>() {
                    override fun getPosition(): Int = adapterPosition
                    override fun getSelectionKey(): Long = itemId
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFViewHolder {
        return PDFViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.doc_holder,parent,false))
    }

    override fun onBindViewHolder(holder: PDFViewHolder, position: Int) {

        val parent = holder.image.parent as LinearLayout
        val isSelected = tracker?.isSelected(position.toLong())?:false
        if (isSelected){
            parent.background = ColorDrawable(Color.parseColor("#000000"))
        }
        else
            parent.background = ColorDrawable(Color.parseColor("#2d2d2d"))
        holder.itemView.apply {
            tvPdfName.text = pdfFiles[position].file.name
            setOnClickListener {
                itemClickListener?.let {click->
                    click(pdfFiles[position])
                }
            }

        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return pdfFiles.size
    }
    private var itemClickListener : ((FileModal) -> Unit)? = null

    fun setTrack(tracker: SelectionTracker<Long>?){
        this.tracker = tracker
    }
    fun setOnItemClickListener(listener : (FileModal) -> Unit){
        itemClickListener = listener
    }

    private val listDiffer:AsyncListDiffer<FileModal> = AsyncListDiffer(this,object : DiffUtil.ItemCallback<FileModal>(){
        override fun areItemsTheSame(oldItem: FileModal, newItem: FileModal): Boolean {
            return oldItem.file.absolutePath == newItem.file.absolutePath
        }

        override fun areContentsTheSame(oldItem: FileModal, newItem: FileModal): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    )
}