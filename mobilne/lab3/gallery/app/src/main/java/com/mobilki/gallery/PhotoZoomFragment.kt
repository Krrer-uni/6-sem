package com.mobilki.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
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
                AsyncImage(model = photo_url?.url,
                    contentDescription = "",
                )
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