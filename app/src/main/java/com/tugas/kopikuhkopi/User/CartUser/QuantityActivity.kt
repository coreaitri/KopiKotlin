package com.tugas.kopikuhkopi.User.CartUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.tugas.kopikuhkopi.R
import com.tugas.kopikuhkopi.User.MenuUser.Constant
import kotlinx.android.synthetic.main.activity_quantity.*
import java.util.HashMap

class QuantityActivity : AppCompatActivity() {

    private var item = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quantity)

        val menuId = intent.getStringExtra(Constant.KEY_ID_MENU)
        val productName = intent.getStringExtra(Constant.KEY_NAME)
        val productPrice = intent.getStringExtra(Constant.KEY_PRICE)
        val imageProduct = intent.getStringExtra(Constant.KEY_IMAGE)

        Glide.with(this)
            .asBitmap()
            .load(imageProduct)
            .into(ivProduct)

        btnIncrement.setOnClickListener {
            val price = productPrice.toInt()
            increment(price)
        }

        btnDecrement.setOnClickListener {
            val price = productPrice.toInt()
            decrement(price)
        }

        btnAddCart.setOnClickListener {
            if (quantity.text.toString() == "0") {
                Toast.makeText(this, "Entry Quantity", Toast.LENGTH_SHORT).show()
            } else {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                val dbCart = FirebaseDatabase.getInstance().getReference("Cart").child(userId!!)
                val dbCartAdmin = FirebaseDatabase.getInstance().getReference("CartAdmin").child(userId!!)

                val hashMap = hashMapOf<String, Any>()
                hashMap.put("userId", FirebaseAuth.getInstance().currentUser!!.uid)
                hashMap.put("menuId", menuId)
                hashMap.put("productName", productName)
                hashMap.put("quantity", quantity.text.toString())
                hashMap.put("total", total.text.toString())


                dbCart.child(menuId).updateChildren(hashMap).addOnCompleteListener {
                    dbCartAdmin.child(menuId).updateChildren(hashMap).addOnCompleteListener {
                        Toast.makeText(this, "product added to cart", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    private fun increment(price: Int) {
        item++
        quantity.text = Integer.toString(item)
        total.text = Integer.toString(sumOfProduct(price))
    }

    private fun decrement(price: Int) {
        if (item > 0) {
            item--
        }
        quantity.text = Integer.toString(item)
        total.text = Integer.toString(sumOfProduct(price))
    }

    private fun sumOfProduct(price: Int): Int {
        return item * price
    }
}
