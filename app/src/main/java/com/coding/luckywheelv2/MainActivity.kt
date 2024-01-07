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
import com.coding.luckywheelv2.R
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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStartWheel = findViewById(R.id.button_start_spin)
        imageWheel = findViewById(R.id.lucky_wheel)
        result = findViewById(R.id.result)

        buttonStartWheel.setOnClickListener {
            if (buttonRotation) {
                oldDegrees = degrees % 360
                // Thay đổi giá trị này để có số phần chia mong muốn (ở đây là 8)
                val sectors = 8
                degrees =  (random.nextInt(3600) + 720).toFloat()
                //degrees = (random.nextInt(8) * 45).toFloat()

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
        val itemIndex = ((degrees + number / 2) / number).toInt()

        when (itemIndex) {
            0 -> showToast("JackPot")
            1 -> showToast("100$")
            2 -> showToast("200$")
            3 -> showToast("300$")
            4 -> showToast("LOSE")
            5 -> showToast("400$")
            6 -> showToast("100$")
            7 -> showToast("200$")
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        result.text = text
    }
}
