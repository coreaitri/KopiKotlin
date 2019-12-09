package com.tugas.kopikuhkopi.User.UserBottom


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

import com.tugas.kopikuhkopi.R
import com.tugas.kopikuhkopi.User.CartUser.CartUserActivity
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivCart.setOnClickListener {
            startActivity(Intent(activity, CartUserActivity::class.java))
        }

        val images = intArrayOf(R.drawable.logo, R.drawable.screen_diseleksi, R.drawable.screen_diproses, R.drawable.screen_disajikan)
        for (image in images) {
            flipImages(image)
        }
    }

      fun flipImages(image: Int) {
         val imageView = ImageView(activity)
         imageView.setImageResource(image)

         vp_flipper_layout.addView(imageView)
         vp_flipper_layout.setFlipInterval(2000)
         vp_flipper_layout.isAutoStart = true

         vp_flipper_layout.setInAnimation(activity, android.R.anim.slide_in_left)
         vp_flipper_layout.setOutAnimation(activity, android.R.anim.slide_out_right)




         Cart.setOnClickListener {
             startActivity(Intent(activity, CartUserActivity::class.java))
         }


         AnimationUtils.loadAnimation(activity, R.anim.cart).also { hyperspaceJumpAnimation ->
            ivCart.startAnimation(hyperspaceJumpAnimation)
         }

     }
}
