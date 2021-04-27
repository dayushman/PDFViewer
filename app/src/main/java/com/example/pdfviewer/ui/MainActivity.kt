package com.example.pdfviewer.ui

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pdfviewer.MainViewModel
import com.example.pdfviewer.R
import com.example.pdfviewer.adaptor.PDFAdaptor
import com.example.pdfviewer.selection.MyItemDetailsLookup
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    var pdfAdaptor: PDFAdaptor? = null
    var tracker : SelectionTracker<Long>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState != null)
            tracker?.onRestoreInstanceState(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        delete.setOnClickListener {
            val number = tracker?.selection?.size()
            if (number == 0)
                Snackbar.make(it, "No Document Selected", Snackbar.LENGTH_LONG).show()
            else{
                val ans = mainViewModel.delete(tracker)
                if (ans)
                    Snackbar.make(it, "Deleted", Snackbar.LENGTH_LONG).setTextColor(Color.parseColor("#ffffff")).show()
                else
                    Snackbar.make(it, "Not Deleted", Snackbar.LENGTH_LONG).setTextColor(Color.parseColor("#ffffff")).show()

            }
        }
        getPermission()
        setObservers()


    }

    private fun setAdaptor() {
        pdfAdaptor = PDFAdaptor()
        pdfAdaptor?.pdfFiles = mainViewModel._listOfDocuments.value!!
        rvAllDocs.layoutManager = GridLayoutManager(this, 3)
        rvAllDocs.adapter = pdfAdaptor

        tracker = SelectionTracker.Builder<Long>(
                "mySelection",
                rvAllDocs,
                StableIdKeyProvider(rvAllDocs),
                MyItemDetailsLookup(rvAllDocs),
                StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
        ).build()


        pdfAdaptor?.setTrack(tracker)


        pdfAdaptor?.setOnItemClickListener {
            startActivity(
                    Intent(this, DocumentViewer::class.java).putExtra("path", it.absolutePath)
            )
        }

    }

    private fun getPermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        mainViewModel.getAllDocs()
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
    }

    private fun setObservers(){
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
        tracker?.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                val nItems: Int? = tracker?.selection?.size()

            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tracker?.onSaveInstanceState(outState)
    }

}