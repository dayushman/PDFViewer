package com.example.pdfviewer

import android.os.Environment
import androidx.lifecycle.*
import androidx.recyclerview.selection.SelectionTracker
import com.example.pdfviewer.modal.FileModal
import com.google.android.material.snackbar.Snackbar
import java.io.File


class MainViewModel: ViewModel() {


  private var listOfDocuments = MutableLiveData<MutableList<FileModal>>()
    val _listOfDocuments : LiveData<MutableList<FileModal>>
    get() = listOfDocuments


     private fun searchFiles(rootFile: File): ArrayList<File> {
        val allFiles = rootFile.listFiles();
        val files = ArrayList<File>()
            allFiles.forEach { file ->
                if (file.isDirectory && !file.isHidden){
                    files.addAll(searchFiles(file))
                }
                else{
                    if (file.name.endsWith(".pdf"))
                        files.add(file)
                }
            }
        return files
    }

    fun getAllDocs() {
        val files = searchFiles(Environment.getExternalStorageDirectory())
        val modal = MutableList(files.size){
            FileModal(files[it],false)
        }
        listOfDocuments.postValue(modal)
    }

    fun delete(tracker: SelectionTracker<Long>?) : Boolean{
        tracker?.selection?.forEach {
            val value = listOfDocuments.value?.get(it.toInt())
            val path = value?.file?.absolutePath
            val file = File(path?:null)
            if (file!=null) {
                if (file.delete()) {
                    listOfDocuments.value?.remove(value)
                }
                else
                    return false
            }
        }
        return true
    }
}