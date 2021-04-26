package com.example.pdfviewer.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    var tracker:SelectionTracker<String>? = null
    var pdfFiles: MutableList<FileModal>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)
    inner class PDFViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<String> =
                object : ItemDetailsLookup.ItemDetails<String>() {
                    override fun getPosition(): Int = adapterPosition
                    override fun getSelectionKey(): String? = pdfFiles[adapterPosition].file.absolutePath
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFViewHolder {
        return PDFViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.doc_holder,parent,false))
    }

    override fun onBindViewHolder(holder: PDFViewHolder, position: Int) {

        val isSelected = tracker?.isSelected(pdfFiles[position].file.absolutePath)?:false
        if (isSelected)

        holder.itemView.apply {
            tvPdfName.text = pdfFiles[position].file.name
            setOnClickListener {
                itemClickListener?.let {click->
                    click(pdfFiles[position])
                }
            }

        }
    }


    override fun getItemCount(): Int {
        return pdfFiles.size
    }
    private var itemClickListener : ((FileModal) -> Unit)? = null


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