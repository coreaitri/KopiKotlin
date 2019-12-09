package com.tugas.kopikuhkopi.Admin.AdminBottom


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tugas.kopikuhkopi.Admin.MenuAdmin.AddMenuActivity
import com.tugas.kopikuhkopi.Admin.MenuAdmin.Menu
import com.tugas.kopikuhkopi.Admin.MenuAdmin.MenuAdapter

import com.tugas.kopikuhkopi.R
import kotlinx.android.synthetic.main.activity_admin_menu.*
import kotlinx.android.synthetic.main.content_admin_main_menu.*
import kotlinx.android.synthetic.main.fragment_menu_admin.*
import kotlinx.android.synthetic.main.fragment_menu_admin.fab

/**
 * A simple [Fragment] subclass.
 */
class MenuAdminFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            startActivity(Intent(activity, AddMenuActivity::class.java))
        }

        val databaseRef = FirebaseDatabase.getInstance().getReference("Menu")
        val menu: ArrayList<Menu> = ArrayList()
        rv_imageCoffee.layoutManager = LinearLayoutManager(activity)

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("Info: ${p0.message}")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    menu.clear()
                    for (data in p0.children){
                        val getValue= data.getValue(Menu::class.java)
                        menu.add(getValue!!)
                }

                rv_imageCoffee.adapter = activity?.let { MenuAdapter(menu, it) }
            }
        }

        })

    }


}
