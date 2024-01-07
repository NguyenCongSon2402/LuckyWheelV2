package com.coding.luckywheelv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Random

class MainActivity2 : AppCompatActivity() {

    private val sectors = arrayOf("JackPot", "100$", "200$", "300$", "LOSE", "400$", "100$", "200$")
    private val sectorsDegrees = IntArray(sectors.size)
    private var radomSectorIndex = 0
    private var spinning = false
    private lateinit var random: Random

    private lateinit var buttonStartWheel: Button
    private lateinit var next: Button
    private lateinit var imageWheel: ImageView
    private lateinit var result: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        buttonStartWheel = findViewById(R.id.button_start_spin)
        next = findViewById(R.id.next)
        imageWheel = findViewById(R.id.lucky_wheel)
        result = findViewById(R.id.result)
        random = Random()
        generateSectorDegrees()
        buttonStartWheel.setOnClickListener {
            if (!spinning) {
                spin()
                spinning = true
            }
        }
        next.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun spin() {
        radomSectorIndex = random.nextInt(sectors.size)
        var radomDegree = generateSectorDegreeToSpinTo()
        val rotateAnimation = RotateAnimation(
            0f, radomDegree.toFloat(),
            RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.duration = 4000
        rotateAnimation.fillAfter = true
        rotateAnimation.interpolator = DecelerateInterpolator()
        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                buttonStartWheel.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animation) {
                var earnedCoins = sectors[sectors.size - (radomSectorIndex + 1)]
                buttonStartWheel.isEnabled = true
                saveEarnings(earnedCoins)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        imageWheel.startAnimation(rotateAnimation)
    }

    private fun saveEarnings(earnedCoins: String) {
        spinning = false
        result.text = earnedCoins
    }

    private fun generateSectorDegreeToSpinTo(): Int {
        return (360 * sectors.size) + sectorsDegrees[radomSectorIndex]
    }

    private fun generateSectorDegrees() {
        var sectorDegree = 360 / sectors.size
        sectors.forEachIndexed { index, _ ->
            sectorsDegrees[index] = (index + 1) * sectorDegree
        }


    }
}