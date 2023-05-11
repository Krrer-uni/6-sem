package com.mobilki.gallery

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class PhotoActivity : AppCompatActivity(), PhotoDetailsFragment.OnDataPass {
    private lateinit var viewPager: ViewPager2
    private var photo : ImageHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_activity)
        window.decorView.apply { windowInsetsController.apply {  } }
        photo = intent.getParcelableExtra("PHOTO_KEY" )

        if (photo == null){
            Log.e("intent error", "given photo is null")
            finish()
        }
        if(photo!!.isLocal){
            val filename = intent.getStringExtra("PHOTO_BITMAP")
            val stream = openFileInput(filename)
            photo!!.bitmap = BitmapFactory.decodeStream(stream)
        }

        val zoomed_photo_fragment = PhotoZoomFragment.newInstance(photo!!)
        val details_photo_fragment = PhotoDetailsFragment.newInstance(photo!!)
        supportFragmentManager.beginTransaction().replace(R.id.photoZoom,zoomed_photo_fragment)
            .replace(R.id.photoDetails,details_photo_fragment).commit()
    }

    override fun onDataPass(data: Any) {
        photo!!.rating = data as Float
    }



    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.putExtra("PHOTO_RETURN", photo)
        setResult(Activity.RESULT_OK,returnIntent)
        finish()
    }

}

class FragmentAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity){
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }

}