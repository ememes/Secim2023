package com.example.secim2023

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mahfa.dnswitch.DayNightSwitch
import com.mahfa.dnswitch.DayNightSwitchListener


class MainActivity : AppCompatActivity() {


    lateinit var textViewGun: TextView
    lateinit var textViewSaat: TextView
    lateinit var textViewDakika: TextView
    lateinit var textViewSaniye: TextView

    lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val relativeLayout = findViewById<RelativeLayout>(R.id.relative_layout)
        val waveAnimation = AnimationUtils.loadAnimation(this, R.anim.wave_anim)
        relativeLayout.startAnimation(waveAnimation)

        textViewGun = findViewById(R.id.text_view_gun)
        textViewSaat = findViewById(R.id.text_view_saat)
        textViewDakika = findViewById(R.id.text_view_dakika)
        textViewSaniye = findViewById(R.id.text_view_saniye)
        val dayNightSwitch: DayNightSwitch = findViewById(R.id.day_night_switch)
        val daySky: View = findViewById(R.id.day_bg)
        val nightSky: View = findViewById(R.id.night_bg)



        dayNightSwitch.setListener { is_night ->
            if (is_night) {

                daySky.animate().alpha(0f).setDuration(1300)
            } else {

                daySky.animate().alpha(1f).setDuration(1300)
            }
        }


        val timeInMillis = calculateTimeInMilliSeconds(2023, 5, 14, 7, 0, 0)

        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                val remainingDays = millisUntilFinished / (24 * 60 * 60 * 1000)
                val remainingHours = millisUntilFinished / (60 * 60 * 1000) % 24
                val remainingMinutes = millisUntilFinished / (60 * 1000) % 60
                val remainingSeconds = millisUntilFinished / 1000 % 60

                textViewGun.text = String.format("%02d", remainingDays)
                textViewSaat.text = String.format("%02d", remainingHours)
                textViewDakika.text = String.format("%02d", remainingMinutes)
                textViewSaniye.text = String.format("%02d", remainingSeconds)
            }

            override fun onFinish() {

            }
        }

        countDownTimer.start()
    }

    private fun calculateTimeInMilliSeconds(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Long {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(year, month - 1, day, hour, minute, second)
        return calendar.timeInMillis - System.currentTimeMillis()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}