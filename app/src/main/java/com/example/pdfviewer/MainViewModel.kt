package com.example.pdfviewer

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File


class MainViewModel: ViewModel() {


    private var listOfDocuments = MutableLiveData<MutableList<File>>()
    val _listOfDocuments : LiveData<MutableList<File>>
        get() = listOfDocuments

    fun searchFiles(rootFile: File): ArrayList<File> {
        val allFiles = rootFile.listFiles();
        var files = ArrayList<File>()
        allFiles.forEach {file ->
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

    fun getAllDocs(){
        listOfDocuments.postValue(searchFiles(Environment.getExternalStorageDirectory()))
    }
}