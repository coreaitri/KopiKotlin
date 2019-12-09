package com.tugas.kopikuhkopi.Admin.AdminBottom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.tugas.kopikuhkopi.R
import kotlinx.android.synthetic.main.layout_admin_bottom.*

class AdminBottomActivity : AppCompatActivity(){

    lateinit var menuAdminFragment: MenuAdminFragment
    lateinit var orderFragment: OrderFragment
    lateinit var settingFragment: SettingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_admin_bottom)

        btn_adminNav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.menuAdmin -> {
                    menuAdminFragment = MenuAdminFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, menuAdminFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

//                R.id.order -> {
//                    orderFragment = OrderFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, orderFragment)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit()
//                }

                R.id.setting -> {
                    settingFragment = SettingFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, settingFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            true
        }
    }
}