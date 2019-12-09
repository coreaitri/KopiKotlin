package com.tugas.kopikuhkopi.Admin.OrderAdmin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tugas.kopikuhkopi.R
import com.tugas.kopikuhkopi.User.CartUser.Cart
import com.tugas.kopikuhkopi.User.CartUser.CartUserAdapter
import com.tugas.kopikuhkopi.User.MenuUser.Constant
import kotlinx.android.synthetic.main.activity_detail_order.*


class DetailOrderActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var cartList: MutableList<Cart>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_order)

        recyclerView = findViewById(R.id.rvdOrder)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cartList = mutableListOf()
        LoadData()


    }

    private fun LoadData()
    {
        val userId = intent.getStringExtra(Constant.KEY_ID_USER)
        val databaseRef = FirebaseDatabase.getInstance().getReference("CartAdmin").child(userId).orderByChild("userId").equalTo(userId)

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError)
            {
                Toast.makeText(applicationContext,"Error Encounter Due to "+databaseError.message, Toast.LENGTH_LONG).show()/**/

            }

            override fun onDataChange(dataSnapshot: DataSnapshot)
            {
                var tt_quantity = 0
                var tt_price = 0
                if (dataSnapshot.exists())
                {//before fetch we have clear the list not to show duplicate value
                    cartList.clear()
                    // fetch data & add to list
                    for (data in dataSnapshot.children)
                    {
                        val std = data.getValue(Cart::class.java)
                        cartList.add(std!!)

                        val t_quantity = data.getValue(Cart::class.java)?.quantity?.toInt()
                        tt_quantity += t_quantity!!

                        val t_price = data.getValue(Cart::class.java)?.total?.toInt()
                        tt_price += t_price!!
                    }

                    val adapter = CartUserAdapter(cartList, this@DetailOrderActivity)
                    rvdOrder.adapter = adapter
                    adapter.notifyDataSetChanged()

                    tvdTotalQuantity.text = tt_quantity.toString()
                    tvdTotalPrice.text = tt_price.toString()

                }
                else
                {
                    // if no data found or you can check specefici child value exist or not here
                    Toast.makeText(applicationContext,"No data Found", Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}
