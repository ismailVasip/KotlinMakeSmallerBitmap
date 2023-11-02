package com.example.kotlinmakesmallbitmap

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.kotlinmakesmallbitmap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }

    fun imageClicked(view : View){

    }

    private fun makeSmallerBitmap(image:Bitmap,maxSize :Int) : Bitmap {
        var width = image.width
        var height = image.height

        var bitmapRatio :Double = width.toDouble() / height.toDouble()

        if(bitmapRatio >1){
            //landscape
            width = maxSize
            val scaledHeight = width / bitmapRatio
            height = scaledHeight.toInt()
        } else {
            //portscape
            height = maxSize
            val scaledWidth = height * bitmapRatio
            width = scaledWidth.toInt()
        }
        return Bitmap.createScaledBitmap(image,width,height,true)
    }
}