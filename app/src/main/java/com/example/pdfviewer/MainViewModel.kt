package com.example.pdfviewer

import android.os.Environment
import androidx.lifecycle.*
import androidx.recyclerview.selection.SelectionTracker
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.nio.file.Files


class MainViewModel: ViewModel() {


  private var listOfDocuments = MutableLiveData<MutableList<File>>()
    val _listOfDocuments : LiveData<MutableList<File>>
    get() = listOfDocuments


     private fun searchFiles(rootFile:File): ArrayList<File> {
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
        listOfDocuments.postValue(files)
    }

    fun delete(tracker: SelectionTracker<Long>?) : Boolean{
        tracker?.selection?.forEach {
            val value = listOfDocuments.value?.get(it.toInt())
            if (value?.exists() == true) {
                if (value.deleteRecursively()) {
                    listOfDocuments.value?.remove(value)
                }
                else{
                    return false
                }
            }
            else
                return false
        }
        tracker?.clearSelection()
        return true
    }
}