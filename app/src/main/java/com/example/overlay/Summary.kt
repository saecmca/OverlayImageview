package com.example.overlay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.overlay.adapter.ImageAdapter
import java.io.File

class Summary : AppCompatActivity() {
    val arrImage = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        val transid = intent.getStringExtra("transactionid")
        getImage(transid!!)
        val rclImageview: RecyclerView = findViewById(R.id.rclview);

        // scroll horizontal view more images
        var adapter = ImageAdapter(arrImage);
        rclImageview.setAdapter(adapter)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rclImageview.setLayoutManager(layoutManager)
    }

    fun getImage(transactionId: String) {
        try {
            var gpath =
                getOutputDirectory().toString()
            var fullpath = File(gpath)
            if (fullpath != null && fullpath.listFiles() != null) {
                for (currentFile in fullpath.listFiles()) {
                    if (currentFile.name.contains(transactionId)) {
                        arrImage.add(currentFile.absolutePath)
                    }
                }
            }
            arrImage.reverse()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
}