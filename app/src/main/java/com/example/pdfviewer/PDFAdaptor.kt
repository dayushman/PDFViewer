package com.example.pdfviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.doc_holder.view.*
import java.io.File

class PDFAdaptor: RecyclerView.Adapter<PDFAdaptor.PDFViewHolder>() {


    private lateinit var onClickListener:(View)->Unit
    var pdfFiles: MutableList<File>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)
    class PDFViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFViewHolder {
        return PDFViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.doc_holder,parent,false))
    }

    override fun onBindViewHolder(holder: PDFViewHolder, position: Int) {
        holder.itemView.apply {
            tvPdfName.text = pdfFiles[position].name
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
    private var itemClickListener : ((File) -> Unit)? = null


    fun setOnItemClickListener(listener : (File) -> Unit){
        itemClickListener = listener
    }

    private val listDiffer:AsyncListDiffer<File> = AsyncListDiffer(this,object : DiffUtil.ItemCallback<File>(){
        override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem.absolutePath == newItem.absolutePath
        }

        override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    )
}