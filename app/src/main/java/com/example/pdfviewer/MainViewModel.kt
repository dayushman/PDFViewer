package com.example.pdfviewer

import android.os.Environment
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.File


class MainViewModel: ViewModel() {


  private var listOfDocuments = MutableLiveData<MutableList<File>>()
    val _listOfDocuments : LiveData<MutableList<File>>
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
            listOfDocuments.postValue(searchFiles(Environment.getExternalStorageDirectory()))

    }
}