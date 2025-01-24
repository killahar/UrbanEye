package com.example.urbaneye.utils

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher

class PhotoManager(private val pickPhotosLauncher: ActivityResultLauncher<Unit>) {
    private val selectedPhotos: MutableList<Uri> = mutableListOf()

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

    fun getPhotos(): List<Uri> = selectedPhotos.toList()
}