package com.mobilki.gallery

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilki.gallery.ui.theme.GalleryTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {


    lateinit var myViewModel: galleryViewModel
    lateinit var selectedFoto: ImageHolder
    private val REQUEST_IMAGE_CAPTURE = "image_capture"
    lateinit var capturedPhoto: Uri
    var photo_id = 0
    private fun createUri(): Uri {
        val filename = "camera_photo_" + photo_id + ".jpg"
        photo_id++
        val imageFile = File(applicationContext.filesDir, filename)
        return FileProvider.getUriForFile(
            applicationContext,
            BuildConfig.APPLICATION_ID + ".provider", imageFile
        )
    }

    val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.i("Intent return", result.toString())
            val resultData = result.data
            if (resultData != null) {
                selectedFoto.rating =
                    resultData.getParcelableExtra<ImageHolder>("PHOTO_RETURN")?.rating
                        ?: selectedFoto.rating
                Log.i("Intent return", "rating changed")
            }
            myViewModel.onImagesChange()
        }

    val getImate = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        Log.i("Intent return", success.toString())
        if (success) {
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, capturedPhoto)
             myViewModel.addImage(bitmap, capturedPhoto.path.toString())
            Log.i("path", capturedPhoto.path.toString())
        }
        myViewModel.onImagesChange()
    }

    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    myViewModel = viewModel()
                    Gallery(
                        imageOnClick = { imageOnClick(it) },
                        galleryViewModel = myViewModel,
                        captureImage = { captureImage() })
                }
            }
        }
    }

    private fun captureImage() {
        checkCameraPermission()
        capturedPhoto = createUri()
        getImate.launch(capturedPhoto)
    }

    private fun imageOnClick(photo: ImageHolder) {
        selectedFoto = photo
        val intent = Intent(this, PhotoActivity::class.java)
        intent.putExtra("PHOTO_KEY", photo)
        if (photo.isLocal) {
            val filename = "bitmap.png"
            val stream = this.openFileOutput(filename, Context.MODE_PRIVATE)
            photo.bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)

            stream.close()
            intent.putExtra("PHOTO_BITMAP", filename)

        }
        getContent.launch(intent)
        Log.i("Intent return", "after getcontent")
    }

    private fun checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }
    }

}