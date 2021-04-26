package com.example.pdfviewer.modal

import java.io.File

data class FileModal(
        val file:File,
        val isSelected:Boolean = false
)
