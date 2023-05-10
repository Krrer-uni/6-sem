package com.mobilki.gallery

import android.content.Intent
import android.content.res.Configuration
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random


data class GalleryUiState(
    var imageList: MutableList<ImageHolder>? = null,
    var numberOfChanges: Int = 0,
    var isLandscape: Boolean = false
)

class galleryViewModel() : ViewModel() {
    private var _uiState = MutableStateFlow(GalleryUiState())
    val uiState: StateFlow<GalleryUiState> = _uiState.asStateFlow()

    fun onImagesChange() {
        _uiState.value.imageList!!.sortWith{ a,b ->
            return@sortWith (b.rating - a.rating).toInt()
        }
        _uiState.value = GalleryUiState(_uiState.value.imageList,_uiState.value.numberOfChanges+1)
        Log.i("image update", _uiState.value.imageList!!.map { it.url }.toString())
    }


    init {
        _uiState.value = GalleryUiState(imageUrls)

    }
}



@Composable
fun Gallery(galleryViewModel: galleryViewModel = viewModel(), imageOnClick : (ImageHolder) -> Unit) {
    val galleryUiState by galleryViewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    var gridWidth = 0
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
           gridWidth = 6
        }
        else -> {
            gridWidth = 3
        }
    }
    Log.e("Gallery","reload")

    LazyVerticalGrid(columns = GridCells.Fixed(gridWidth),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),

        content = {
            items(galleryUiState.imageList!!) { photo ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(photo.url).build(),
                    contentDescription = photo.description,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .aspectRatio(1f)
                        .clickable {
                            Log.i("Intent return","image clicked")
                            imageOnClick(photo)
                        }
                )
            }
        })
}


data class ImageHolder(val url: String?, val isLocal: Boolean) : Parcelable{
    var rating: Float = 0f
    var description: String? = ""

    init {
        description = LoremIpsum(40).values.fold("") { a, b -> "$a $b" }
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
        rating = parcel.readFloat()
        description = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeByte(if (isLocal) 1 else 0)
        parcel.writeFloat(rating)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageHolder> {
        override fun createFromParcel(parcel: Parcel): ImageHolder {
            return ImageHolder(parcel)
        }

        override fun newArray(size: Int): Array<ImageHolder?> {
            return arrayOfNulls(size)
        }
    }

}


val imageUrls = MutableList(200) {
    ImageHolder("https://picsum.photos/seed/" + (Random.nextInt()).toString() + "/1500", false)
}
