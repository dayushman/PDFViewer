package com.example.pdfviewer

import android.os.Environment
import androidx.lifecycle.*
import com.example.pdfviewer.modal.FileModal
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
}