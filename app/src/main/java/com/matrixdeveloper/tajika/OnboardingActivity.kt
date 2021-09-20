package com.matrixdeveloper.tajika

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.matrixdeveloper.tajika.adapter.OnboardingViewPagerAdapter4
import com.matrixdeveloper.tajika.utils.PrefManager

class OnboardingActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager
    //private lateinit var btnBack: Button
    private lateinit var btnNext: TextView
    private var prf: PrefManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        prf = PrefManager(this@OnboardingActivity)
        mViewPager = findViewById(R.id.viewPager)
        mViewPager.adapter = OnboardingViewPagerAdapter4(supportFragmentManager, this)
        mViewPager.offscreenPageLimit = 1
        //btnBack = btn_previous_step
        btnNext = findViewById(R.id.btn_next_step)
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    btnNext.text = getText(R.string.finish)
                } else {
                    btnNext.text = getText(R.string.next)
                }
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        })

        btnNext.setOnClickListener {
            if (getItem(+1) > mViewPager.childCount-1) {
                prf!!.setBoolean("firstLaunch", false)
                startActivity(Intent(this, LandingPage::class.java))
                finish()
            } else {
                mViewPager.setCurrentItem(getItem(+1), true)
            }
        }

            /*btnBack.setOnClickListener {
            if (getItem(+1) == 1) {
//                startActivity(Intent(this, LandingPage::class.java))
//                finish()
            } else {
                mViewPager.setCurrentItem(getItem(-1), true)
            }
        }*/
    }

    private fun getItem(i: Int): Int {
        return mViewPager.currentItem + i
    }
}
