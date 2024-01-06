package com.coding.luckywheelv2

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var buttonRotation = true
    private val number = 45f

    private var degrees = 0f
    private var oldDegrees = 0f
    private lateinit var buttonStartWheel: Button
    private lateinit var imageWheel: ImageView
    private lateinit var result: TextView
    private val random = Random

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(1024);
        supportRequestWindowFeature(1);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStartWheel = findViewById(R.id.button_start_spin)
        imageWheel = findViewById(R.id.lucky_wheel)
        result = findViewById(R.id.result)

        buttonStartWheel.setOnClickListener {
            if (buttonRotation) {
                oldDegrees = degrees % 360
                degrees = (random.nextInt(3600) + 720).toFloat()
                val rotateAnimation = RotateAnimation(
                    oldDegrees, degrees,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f
                )
                rotateAnimation.duration = 4000
                rotateAnimation.fillAfter = true
                rotateAnimation.interpolator = DecelerateInterpolator()
                rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {
                        buttonRotation = false
                        buttonStartWheel.isEnabled = false
                    }

                    override fun onAnimationEnd(animation: Animation) {
                        buttonRotation = true
                        buttonStartWheel.isEnabled = true
                        currentNumber(360 - (degrees % 360))
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
                imageWheel.startAnimation(rotateAnimation)
            }
        }
    }

    private fun currentNumber(degrees: Float) {
//        val itemIndex = ((degrees + number / 2) / number).toInt()
//
//        when (itemIndex) {
//            0 -> showToast("JackPot")
//            1 -> showToast("100$")
//            2 -> showToast("200$")
//            3 -> showToast("300$")
//            4 -> showToast("LOSE")
//            5 -> showToast("400$")
//            6 -> showToast("100$")
//            7 -> showToast("200$")
//        }
        when {
            degrees >= (number * 0) && degrees < (number * 1) -> showToast("JackPot")
            degrees >= (number * 1) && degrees < (number * 2) -> showToast("100$")
            degrees >= (number * 2) && degrees < (number * 3) -> showToast("200$")
            degrees >= (number * 3) && degrees < (number * 4) -> showToast("300$")
            degrees >= (number * 4) && degrees < (number * 5) -> showToast("LOSE")
            degrees >= (number * 5) && degrees < (number * 6) -> showToast("400$")
            degrees >= (number * 6) && degrees < (number * 7) -> showToast("100$")
            degrees >= (number * 7) && degrees < (number * 8) -> showToast("200$")
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        result.text = text
    }
}
