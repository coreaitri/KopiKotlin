package com.tugas.kopikuhkopi.Admin.MenuAdmin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tugas.kopikuhkopi.R
import kotlinx.android.synthetic.main.activity_admin_menu.*
import kotlinx.android.synthetic.main.content_admin_main_menu.*


class MenuAdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_menu)

        //button command
        fab.setOnClickListener {
            startActivity(Intent(this, AddMenuActivity::class.java))
        }

        //init referensi
        val databaseRef = FirebaseDatabase.getInstance().getReference("Menu")


        val menu: ArrayList<Menu> = ArrayList()
        rv_imageCoffee.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("Info: ${p0.message}")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    menu.clear()
                    for(data in p0.children){
                        val getValue = data.getValue(Menu::class.java)
                        menu.add(getValue!!)
                    }
                }
                rv_imageCoffee.adapter = MenuAdapter( menu, this@MenuAdminActivity)
            }
        })
    }
}