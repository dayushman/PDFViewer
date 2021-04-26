package com.example.pdfviewer

import android.Manifest
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel:MainViewModel
    var pdfAdaptor: PDFAdaptor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        getPermission()
        setObservers()


    }

    private fun setAdaptor() {
        pdfAdaptor = PDFAdaptor()
        pdfAdaptor?.pdfFiles = mainViewModel._listOfDocuments.value!!
        rvAllDocs.layoutManager = GridLayoutManager(this, 3)
        rvAllDocs.adapter = pdfAdaptor
        pdfAdaptor?.setOnItemClickListener {
            startActivity(
                Intent(this,DocumentViewer::class.java).putExtra("path",it.absolutePath)
            )
        }

    }

    private fun getPermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                    mainViewModel.getAllDocs()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                    Snackbar.make(rvAllDocs, "Permission required", Snackbar.LENGTH_LONG)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) { /* ... */
                    token?.continuePermissionRequest()
                }
            }).check()
    }

    fun setObservers(){
        mainViewModel._listOfDocuments.observe(this){
            if (it!=null){
                if (pdfAdaptor==null){
                    setAdaptor()
                    progressBar.visibility = View.GONE
                }
                else
                    pdfAdaptor?.pdfFiles = it
            }
        }
    }
}