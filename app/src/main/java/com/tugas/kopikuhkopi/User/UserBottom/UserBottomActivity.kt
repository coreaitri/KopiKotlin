package com.tugas.kopikuhkopi.User.UserBottom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.tugas.kopikuhkopi.R
import kotlinx.android.synthetic.main.layout_user_bottom.*

class UserBottomActivity: AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var menuUserFragment: MenuUserFragment
    lateinit var cartFragment: CartFragment
    lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_user_bottom)

        btn_nav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.menu -> {
                    menuUserFragment = MenuUserFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, menuUserFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

//                R.id.cart -> {
//                    cartFragment = CartFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, cartFragment)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit()
//                }

                R.id.profile -> {
                    profileFragment = ProfileFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, profileFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

            }
            true
        }
    }
}