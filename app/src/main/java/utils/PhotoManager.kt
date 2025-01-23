package com.example.urbaneye.utils

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class PhotoManager(private val pickPhotosLauncher: ActivityResultLauncher<Unit>) {
    var selectedPhotos: MutableList<Uri> = mutableListOf()

    fun pickPhotos() {
        pickPhotosLauncher.launch(Unit)
    }

    fun addPhoto(uri: Uri) {
        if (selectedPhotos.size < 10) {
            selectedPhotos.add(uri)
        }
    }

    fun removePhoto(uri: Uri) {
        selectedPhotos.remove(uri)
    }

    fun clearPhotos() {
        selectedPhotos.clear()
    }
}