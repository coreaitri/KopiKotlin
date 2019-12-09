package com.tugas.kopikuhkopi.User.CartUser

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tugas.kopikuhkopi.R
import com.tugas.kopikuhkopi.User.MenuUser.Constant
import com.tugas.kopikuhkopi.User.MenuUser.MenuUserActivity
import com.tugas.kopikuhkopi.User.UserBottom.ProfileFragment
import kotlinx.android.synthetic.main.activity_cart.*


class CartUserActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var cartList: MutableList<Cart>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.rvCart)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cartList = mutableListOf()
        LoadData()

        btnCheckout.setOnClickListener {
            val cartId = FirebaseDatabase.getInstance().getReference("Cart").key.toString()

            val totalCart = totalPrice.text.toString()

            val intent = Intent(this, ConfirmOrder::class.java)
            intent.putExtra(Constant.KEY_TOTAL_CHART, totalCart)
            intent.putExtra("cartId",cartId)
            startActivity(intent)
        }

        addCart.setOnClickListener {
            startActivity(Intent(this, MenuUserActivity::class.java))
        }
    }

    private fun LoadData()
    {
        val akunUser = FirebaseAuth.getInstance().currentUser?.uid
        val databaseRef = FirebaseDatabase.getInstance().getReference("Cart").child(akunUser!!).orderByChild("userId").equalTo(akunUser)

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

                    val adapter = CartUserAdapter(cartList, this@CartUserActivity)
                    rvCart.adapter = adapter
                    adapter.notifyDataSetChanged()

                    totalItem.text = tt_quantity.toString()
                    totalPrice.text = tt_price.toString()

                }
                else
                {
                    Toast.makeText(applicationContext,"No data Found", Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}
