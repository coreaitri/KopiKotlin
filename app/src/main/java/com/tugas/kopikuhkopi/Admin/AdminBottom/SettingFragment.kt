package com.tugas.kopikuhkopi.Admin.AdminBottom


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.tugas.kopikuhkopi.Admin.MenuAdmin.MenuAdminActivity
import com.tugas.kopikuhkopi.Admin.OrderAdmin.OrderActivity
import com.tugas.kopikuhkopi.LoginActivity

import com.tugas.kopikuhkopi.R
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_logoutAdmin.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        adminMenu.setOnClickListener{
            val intent = Intent(activity, MenuAdminActivity::class.java)
            startActivity(intent)
        }

        adminOrder.setOnClickListener {
            val intent = Intent(activity, OrderActivity::class.java)
            startActivity(intent)
        }


    }


}
