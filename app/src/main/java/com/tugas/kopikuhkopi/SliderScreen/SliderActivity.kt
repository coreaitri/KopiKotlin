package com.tugas.kopikuhkopi.SliderScreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.tugas.kopikuhkopi.LoginActivity
import com.tugas.kopikuhkopi.MainActivity
import com.tugas.kopikuhkopi.R

class SliderActivity : AppCompatActivity() {

    private var screenPager: ViewPager? = null
    internal lateinit var sliderViewPagerAdapter: SliderViewPagerAdapter
    internal lateinit var btnNext: Button
    internal var position = 0
    internal lateinit var btnGetStarted: Button
    internal lateinit var btnAnim: Animation
    internal lateinit var tvSkip: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        if (restorePrefData()) {

            val mainActivity = Intent(applicationContext, LoginActivity::class.java)
            startActivity(mainActivity)
            finish()


        }

        setContentView(R.layout.activity_slider)
        supportActionBar?.hide()

        btnNext = findViewById(R.id.btn_next)
        btnGetStarted = findViewById(R.id.btn_get_started)
        btnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)
        tvSkip = findViewById(R.id.tv_skip)

        // fill list screen

        val mList = ArrayList<ScreenItem>()
        mList.add(
            ScreenItem(
                "DIPILIH",
                "Dipilih dari tanaman Kopi terbaik di berbagai daerah indonesia.",
                R.drawable.screen_dipilih
            )
        )
        mList.add(
            ScreenItem(
                "DISELEKSI",
                "Diseleksi kembali dari antara kopi yang telah terpilih",
                R.drawable.screen_diseleksi
            )
        )
        mList.add(
            ScreenItem(
                "DIPROSES",
                "Diproses sepenuh hati dengan teknologi canggih dan bersih",
                R.drawable.screen_diproses
            )
        )
        mList.add(
            ScreenItem(
                "DISAJIKAN",
                "Disajikan untuk kamu yang terseleksi, terpilih dan beruntung",
                R.drawable.screen_disajikan
            )
        )

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager)
        sliderViewPagerAdapter = SliderViewPagerAdapter(this, mList)
        screenPager!!.setAdapter(sliderViewPagerAdapter)


        btnNext.setOnClickListener {
            position = screenPager!!.getCurrentItem()
            if (position < mList.size) {

                position++
                screenPager!!.setCurrentItem(position)


            }

            if (position == mList.size - 1) { // when we rech to the last screen

                // TODO : show the GETSTARTED Button and hide the indicator and the next button

                loadLastScreen()


            }
        }

        btnGetStarted.setOnClickListener {
            //open main activity

            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            savePrefsData()
            finish()
        }

        // skip button click listener

        tvSkip.setOnClickListener { screenPager!!.setCurrentItem(mList.size) }


    }

    private fun restorePrefData(): Boolean {


        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        return pref.getBoolean("isIntroOpened", false)


    }

    private fun savePrefsData() {

        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isIntroOpened", true)
        editor.commit()


    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private fun loadLastScreen() {

        btnNext.visibility = View.INVISIBLE
        btnGetStarted.visibility = View.VISIBLE
        tvSkip.visibility = View.INVISIBLE
        btnGetStarted.animation = btnAnim


    }
}
