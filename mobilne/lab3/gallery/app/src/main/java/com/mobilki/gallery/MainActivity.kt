package com.mobilki.gallery

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilki.gallery.ui.theme.GalleryTheme

class MainActivity : ComponentActivity() {


    lateinit var myViewModel : galleryViewModel
    lateinit var selectedFoto : ImageHolder

    var contentResult : Intent? = null
    val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.i("Intent return",result.toString())
        val resultData = result.data
        if (resultData != null) {
            selectedFoto.rating = resultData.getParcelableExtra<ImageHolder>("PHOTO_RETURN")?.rating
                ?: selectedFoto.rating
            Log.i("Intent return","rating changed")
        }
        myViewModel.onImagesChange()
    }


    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null

        fun applicationContext() : Context {
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
                    myViewModel  = viewModel()
                    Gallery(imageOnClick = {imageOnClick(it)}, galleryViewModel = myViewModel)
                }
            }
        }
    }


    private fun imageOnClick(photo : ImageHolder): Intent? {
        selectedFoto = photo
        val intent = Intent(this, PhotoActivity::class.java)
        intent.putExtra("PHOTO_KEY",photo)
        getContent.launch(intent)
        Log.i("Intent return","after getcontent")
        return contentResult
    }


}