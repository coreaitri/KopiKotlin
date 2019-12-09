package com.tugas.kopikuhkopi.Admin.MenuAdmin

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.tugas.kopikuhkopi.R
import com.tugas.kopikuhkopi.User.UserBottom.ProfileFragment
import kotlinx.android.synthetic.main.user_item_menu.*

class MenuAdapter(val menuList: List<Menu>, val context: Context) : RecyclerView.Adapter<MenuAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_item_menu, parent, false)
        return Holder(view)

    }

    override fun getItemCount(): Int
    {
        return menuList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int)
    {
        holder.name.text = menuList[position].name
        holder.price.text = menuList[position].price
        Glide.with(context).load(menuList[position].image).into(holder.image)

        // if user click on update icon for  update operation
        holder.edit.setOnClickListener()
        {
            val perItemPosition = menuList[position]
            updateDialog(perItemPosition)
        }

        // if user click on delete icon for delete operation
        holder.delete.setOnClickListener()
        {
            val perItemPosition = menuList[position]
            deleteData(perItemPosition.menuId)

        }

    }


    // holder class
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById(R.id.tv_nameMenu) as TextView
        val price = itemView.findViewById(R.id.tv_priceMenu) as TextView
        val image = itemView.findViewById(R.id.iv_imageMenu) as ImageView

        // action operation widget
        val edit = itemView.findViewById(R.id.fabEdit) as FloatingActionButton
        val delete = itemView.findViewById(R.id.fabDelete) as FloatingActionButton

    }

    // update dialog show method
    private fun updateDialog(perItemPosition: Menu) {

        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.activity_admin_update_menu, null)
        builder.setCancelable(false)

        val updateName = view.findViewById<EditText>(R.id.etu_name)
        val updatePrice = view.findViewById<EditText>(R.id.etu_price)

        // set exist data from recycler to dialog field
        updateName.setText(perItemPosition.name)
        updatePrice.setText(perItemPosition.price)

        // now set view to builder
        builder.setView(view)
        // now set positive negative button in alertdialog
        builder.setPositiveButton("Update", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                // update operation below
                val databaseRef = FirebaseDatabase.getInstance().getReference("Menu")

                val name = updateName.text.toString()
                val price = updatePrice.text.toString()
                val image = perItemPosition.image

                if (name.isEmpty() && price.isEmpty())
                {
                    updateName.error = "please Fill up data"
                    updateName.requestFocus()
                    return
                }
                else
                {
                    // update data
                    val stdData = Menu(perItemPosition.menuId,name,price,image)
                    databaseRef.child("${perItemPosition.menuId}").setValue(stdData)
                    Toast.makeText(context, "Data Updated", Toast.LENGTH_SHORT).show()

                }


            }
        })

        builder.setNegativeButton("No", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {


            }
        })
        // show dialog now
        val alert = builder.create()
        alert.show()
    }

    // delete operation
    private fun deleteData(menuId: String)
    {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Menu").child(menuId)
        databaseRef.removeValue().addOnCompleteListener()
        {
            Toast.makeText(context, "Data Deleted Successfully", Toast.LENGTH_SHORT).show()
        }

    }
}



//    (val menuList: List<Menu>, val context: Context) : RecyclerView.Adapter<MenuAdapter.Holder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
//    {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_item_menu, parent, false)
//        return Holder(view)
//    }
//
//    override fun getItemCount(): Int
//    {
//        return menuList.size
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int)
//    {
//        holder.name.text = menuList[position].name
//        holder.price.text = menuList[position].price
//        Glide.with(context).load(menuList[position].image).into(holder.image)
//
//        // if user click on update icon for  update operation
//        holder.edit.setOnClickListener()
//        {
//            val perItemPosition = menuList[position]
//            updateDialog(perItemPosition)
//        }
//
//        // if user click on delete icon for delete operation
//        holder.delete.setOnClickListener()
//        {
//            val perItemPosition = menuList[position]
//            deleteData(perItemPosition.menuId)
//
//        }
//
//    }
//
//
//    // holder class
//    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        val name = itemView.findViewById(R.id.tv_nameMenu) as TextView
//        val price = itemView.findViewById(R.id.tv_priceMenu) as TextView
//        val image = itemView.findViewById(R.id.iv_imageMenu) as ImageView
//
//        // action operation widget
//        val edit = itemView.findViewById(R.id.fabEdit) as FloatingActionButton
//        val delete = itemView.findViewById(R.id.fabDelete) as FloatingActionButton
//
//    }
//
//    // update dialog show method
//    private fun updateDialog(perItemPosition: Menu) {
//
//        val builder = AlertDialog.Builder(context)
//        val inflater = LayoutInflater.from(context)
//        val view = inflater.inflate(R.layout.activity_admin_update_menu, null)
//        builder.setCancelable(false)
//
//        val updateName = view.findViewById<EditText>(R.id.etu_name)
//        val updatePrice = view.findViewById<EditText>(R.id.etu_price)
//
//        // set exist data from recycler to dialog field
//        updateName.setText(perItemPosition.name)
//        updatePrice.setText(perItemPosition.price)
//
//        // now set view to builder
//        builder.setView(view)
//        // now set positive negative button in alertdialog
//        builder.setPositiveButton("Update", object : DialogInterface.OnClickListener{
//            override fun onClick(dialog: DialogInterface?, which: Int) {
//
//                // update operation below
//                val databaseRef = FirebaseDatabase.getInstance().getReference("menus")
//
//                val name = updateName.text.toString()
//                val price = updatePrice.text.toString()
//                val image = perItemPosition.image
//
//                if (name.isEmpty() && price.isEmpty())
//                {
//                    updateName.error = "please Fill up data"
//                    updateName.requestFocus()
//                    return
//                }
//                else
//                {
//                    // update data
//                    val stdData = Menu(perItemPosition.menuId,name,price,image)
//                    databaseRef.child(perItemPosition.menuId).setValue(stdData)
//                    Toast.makeText(context, "Data Updated", Toast.LENGTH_SHORT).show()
//
//                }
//
//
//            }
//        })
//
//        builder.setNegativeButton("No", object : DialogInterface.OnClickListener{
//            override fun onClick(dialog: DialogInterface?, which: Int) {
//
//            }
//        })
//        // show dialog now
//        val alert = builder.create()
//        alert.show()
//    }
//
//    // delete operation
//    private fun deleteData(menuId: String)
//    {
//        val databaseRef = FirebaseDatabase.getInstance().getReference("menus").child(menuId)
//        databaseRef.removeValue().addOnCompleteListener()
//        {
//            Toast.makeText(context, "Data Deleted Successfully", Toast.LENGTH_SHORT).show()
//        }
//
//    }
//
//
//}