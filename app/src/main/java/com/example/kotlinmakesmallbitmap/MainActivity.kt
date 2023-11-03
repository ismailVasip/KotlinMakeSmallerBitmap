package com.example.kotlinmakesmallbitmap

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.kotlinmakesmallbitmap.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    private lateinit var smallBitmap : Bitmap
    private var selectedBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }

    fun saveClicked(view:View){
        //Recording image to SQLite server
        if(selectedBitmap != null){
            smallBitmap = makeSmallerBitmap(selectedBitmap!!,300)

            val outputStream = ByteArrayOutputStream()
            smallBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
            val byteArray = outputStream.toByteArray()

            try{
                val database = this.openOrCreateDatabase("Arts", MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY,image BLOB)")

                val sqlString= "INSERT INTO arts (image) VALUES(?)"
                val statement = database.compileStatement(sqlString)
                statement.bindBlob(1,byteArray)
                statement.execute()

            } catch (e:Exception){
                e.printStackTrace()
            }
        }

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