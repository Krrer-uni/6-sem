package com.mobilki.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val ARG_PHOTO = "param_photo"

class PhotoDetailsFragment() : Fragment() {

    private var photo_param: ImageHolder? = null
    private var new_rating : Float? = null

    interface OnDataPass{
        fun onDataPass(data: Any)
    }

    private lateinit var parent_data_bridge: OnDataPass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photo_param = it.getParcelable(ARG_PHOTO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val ui = inflater.inflate(R.layout.fragment_photo_details, container, false)
        val photo_desc = ui.findViewById<TextView>(R.id.photoDescription)
        photo_desc.text = photo_param?.description
        val photo_rating = ui.findViewById<RatingBar>(R.id.ratingBar)
        photo_rating.rating = photo_param?.rating?.toFloat() ?: 0f
        photo_rating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if(fromUser){
                new_rating = rating
                parent_data_bridge.onDataPass(rating)
            }
        }
        return ui
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parent_data_bridge = context as OnDataPass
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
            PhotoDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PHOTO, photo_name)
                }
            }
    }

}

