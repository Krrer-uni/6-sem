package com.mobilki.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PHOTO = "photo_param"


/**
 * A simple [Fragment] subclass.
 * Use the [PhotoZoomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotoZoomFragment : Fragment() {
    private var photo_url: ImageHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photo_url = it.getParcelable(ARG_PHOTO)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                if(photo_url!!.isLocal){
                    Image(photo_url!!.bitmap!!.asImageBitmap(),"", modifier = Modifier.aspectRatio(1f))
                }else{
                    AsyncImage(model = photo_url?.url,
                        contentDescription = "",
                    )
                }

            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param photo_name Parameter 1.
         * @return A new instance of fragment PhotoZoom.
         */
        @JvmStatic
        fun newInstance(photo_name: ImageHolder) =
            PhotoZoomFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PHOTO, photo_name)
                }
            }
    }
}