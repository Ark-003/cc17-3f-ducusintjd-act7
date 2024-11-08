package com.example.cc17_3f_ducusintjd_act7

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private val imageList = listOf(
        R.drawable.carousel1,
        R.drawable.carousel2,
        R.drawable.carousel3,
        R.drawable.carousel4,
        R.drawable.carousel5,
        R.drawable.carousel6
    )
    private var currentImageIndex = 0
    private lateinit var backgroundImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        backgroundImage = findViewById(R.id.background_image)
        handler = Handler(Looper.getMainLooper())
        updateBackgroundImage()
    }

    private fun updateBackgroundImage() {
        val newImage = imageList[currentImageIndex]

        // Create a fade-out animation for the current image
        val fadeOut = ObjectAnimator.ofFloat(backgroundImage, "alpha", 1f, 0f)
        fadeOut.duration = 500 // Adjust duration as needed

        // Create a fade-in animation for the new image
        val fadeIn = ObjectAnimator.ofFloat(backgroundImage, "alpha", 0f, 1f)
        fadeIn.duration = 500 // Same duration as fade-out

        // AnimatorSet to play fade-out and fade-in animations sequentially
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(fadeOut, fadeIn)

        // Set an end action for the fade-out to change the image
        fadeOut.addUpdateListener {
            if (it.animatedFraction == 1f) {
                // Change the image resource only after fade-out completes
                backgroundImage.setImageResource(newImage)
                backgroundImage.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }

        // Start the AnimatorSet
        animatorSet.start()

        // Schedule the next image change after the fade-out and fade-in animations complete
        handler.postDelayed({
            currentImageIndex = (currentImageIndex + 1) % imageList.size
            updateBackgroundImage()
        }, 6000) // Adjust the delay as needed
    }
}