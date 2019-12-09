package com.tugas.kopikuhkopi.User.UserBottom


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tugas.kopikuhkopi.Admin.MenuAdmin.AddMenuActivity
import com.tugas.kopikuhkopi.Admin.MenuAdmin.Menu
import com.tugas.kopikuhkopi.Admin.MenuAdmin.MenuAdapter

import com.tugas.kopikuhkopi.R
import com.tugas.kopikuhkopi.User.MenuUser.MenuUserAdapter
import kotlinx.android.synthetic.main.content_admin_main_menu.*
import kotlinx.android.synthetic.main.fragment_menu_admin.*
import kotlinx.android.synthetic.main.fragment_menu_user.*
import kotlinx.android.synthetic.main.user_item_menu.*

/**
 * A simple [Fragment] subclass.
 */
class MenuUserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        rvu_imageCoffee.setOnClickListener {
//            startActivity(Intent(activity, ProfileFragment::class.java))
//        }

        val databaseRef = FirebaseDatabase.getInstance().getReference("Menu")
        val menu: ArrayList<Menu> = ArrayList()
        rvu_imageCoffee.layoutManager = GridLayoutManager(activity,2)

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

                    rvu_imageCoffee.adapter = activity?.let { MenuUserAdapter(menu, it!!) }
                }
            }

        })

    }


}
