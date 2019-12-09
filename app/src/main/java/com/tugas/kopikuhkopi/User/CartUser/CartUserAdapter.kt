package com.tugas.kopikuhkopi.User.CartUser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.tugas.kopikuhkopi.R


class CartUserAdapter(val cartList: List<Cart>, val context: Context) : RecyclerView.Adapter<CartUserAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.user_item_cart,parent,false))
    }

    override fun getItemCount(): Int
    {
        return cartList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int)
    {
        holder.nameCart.text = cartList[position].name
        holder.quantityCart.text = cartList[position].quantity
        holder.priceCart.text = cartList[position].total

//        holder.delete.setOnClickListener()
//        {
//            val perItemPosition = cartList[position]
//            deleteData(perItemPosition.menuId)
//        }
    }

//    private fun deleteData(menuId: String) {
//
//        val userAccount = FirebaseAuth.getInstance().currentUser?.uid
//
//        val databaseRef = FirebaseDatabase.getInstance().getReference("Cart").child(userAccount!!).child(menuId).child("${menuId}")
//        databaseRef.removeValue().addOnCompleteListener()
//        {
//            Toast.makeText(context, "Data Deleted Successfully", Toast.LENGTH_SHORT).show()
//        }
//
//        val databaseRefAdmin = FirebaseDatabase.getInstance().getReference("CartAdmin").child(userAccount).child("${menuId}")
//        databaseRefAdmin.removeValue().addOnCompleteListener()
//        {
//            Toast.makeText(context, "Data Deleted Successfully", Toast.LENGTH_SHORT).show()
//        }
//    }

    // holder class
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameCart = itemView.findViewById(R.id.tvNameCart) as TextView
        val quantityCart = itemView.findViewById(R.id.tvQuantityCart) as TextView
        val priceCart = itemView.findViewById(R.id.tvPriceCart) as TextView

//        val delete = itemView.findViewById(R.id.delete) as TextView
    }

}