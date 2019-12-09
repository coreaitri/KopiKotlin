package com.tugas.kopikuhkopi.User.MenuUser

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tugas.kopikuhkopi.Admin.MenuAdmin.Menu
import com.tugas.kopikuhkopi.R
import com.tugas.kopikuhkopi.User.CartUser.QuantityActivity
import com.tugas.kopikuhkopi.User.UserBottom.ProfileFragment


class MenuUserAdapter(val menuList: List<Menu>, val context: Context) : RecyclerView.Adapter<MenuUserAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item_menu, parent, false)
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



        holder.itemView.setOnClickListener()
        {
            val productId = menuList[position].menuId
            val productName = menuList[position].name
            val productPrice = menuList[position].price
            val productImage = menuList[position].image

            val intent = Intent(context, QuantityActivity::class.java)
            intent.putExtra(Constant.KEY_ID_MENU, productId)
            intent.putExtra(Constant.KEY_NAME, productName)
            intent.putExtra(Constant.KEY_PRICE, productPrice)
            intent.putExtra(Constant.KEY_IMAGE, productImage)
            context.startActivity(intent)
        }

    }

    // holder class
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById(R.id.tv_userNameMenu) as TextView
        val price = itemView.findViewById(R.id.tv_userPriceMenu) as TextView
        val image = itemView.findViewById(R.id.iv_userImageMenu) as ImageView

    }

}