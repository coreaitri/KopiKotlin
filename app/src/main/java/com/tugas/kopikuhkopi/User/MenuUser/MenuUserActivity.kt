package com.tugas.kopikuhkopi.User.MenuUser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tugas.kopikuhkopi.Admin.MenuAdmin.Menu
import com.tugas.kopikuhkopi.R
import kotlinx.android.synthetic.main.activity_user_menu.*


class MenuUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_menu)

        //init referensi
        val databaseRef = FirebaseDatabase.getInstance().getReference("Menu")


        val menu: ArrayList<Menu> = ArrayList()
        rvUser_imageCoffee.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

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
                rvUser_imageCoffee.adapter = MenuUserAdapter( menu, this@MenuUserActivity)
            }
        })
    }
}