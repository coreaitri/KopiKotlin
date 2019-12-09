package com.tugas.kopikuhkopi.Admin.OrderAdmin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tugas.kopikuhkopi.R
import com.tugas.kopikuhkopi.User.MenuUser.Constant

class OrderAdapter(val orderList: List<Order>, val context: Context) : RecyclerView.Adapter<OrderAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin_order, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int
    {
        return orderList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int)
    {
        holder.orderId.text = orderList[position].orderId
        holder.name.text = orderList[position].name
        holder.numberPhone.text = orderList[position].numberPhone
        holder.address.text = orderList[position].address
        holder.total.text = orderList[position].total

        holder.ordered.setOnClickListener {
            val userId = orderList[position].userId

            val intent = Intent(context, DetailOrderActivity::class.java)
            intent.putExtra(Constant.KEY_ID_USER, userId)
            context.startActivity(intent)
        }
    }


    // holder class
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val orderId = itemView.findViewById(R.id.tvoOrderId) as TextView
        val name = itemView.findViewById(R.id.tvoName) as TextView
        val numberPhone = itemView.findViewById(R.id.tvoNumberHP) as TextView
        val address = itemView.findViewById(R.id.tvoAddress) as TextView
        val total = itemView.findViewById(R.id.tvoTotal) as TextView

        val ordered = itemView.findViewById(R.id.btnLookOrder) as Button
    }
}