package com.tugas.kopikuhkopi.User.UserBottom


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tugas.kopikuhkopi.LoginActivity
import com.tugas.kopikuhkopi.R
import com.tugas.kopikuhkopi.User.CartUser.Cart
import com.tugas.kopikuhkopi.User.CartUser.CartUserAdapter
import com.tugas.kopikuhkopi.User.MenuUser.Constant
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.fragment_cart.*
import com.tugas.kopikuhkopi.User.UserBottom.CartFragment as CartFragment


/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartList: MutableList<Cart> = mutableListOf()

        rvCart.setHasFixedSize(true)
        rvCart.layoutManager = LinearLayoutManager(activity)

        btnCheckout.setOnClickListener {
            val cartId = FirebaseDatabase.getInstance().getReference("Cart").key.toString()
            val totalCart = totalPrice.text.toString()

            val intent = Intent(activity, ProfileFragment::class.java)
            intent.putExtra(Constant.KEY_TOTAL_CHART, totalCart)
            intent.putExtra("cartId", cartId)
            startActivity(intent)
        }

        addCart.setOnClickListener {
            startActivity(Intent(activity, HomeFragment::class.java))
        }

        val userAccount = FirebaseAuth.getInstance().currentUser?.uid
        val databaseRef = FirebaseDatabase.getInstance().getReference("Cart").child(userAccount!!).orderByChild("userId").equalTo(userAccount)

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity,"Error Encounter Due to "+databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot){
                var total_item = 0
                var total_price = 0
                if (dataSnapshot.exists()){
                    cartList.clear()
                    // fetch data & add to list
                    for (data in dataSnapshot.children) {
                        val std = data.getValue(Cart::class.java)
                        cartList.add(std!!)

                        val total_item1 = data.getValue(Cart::class.java)?.quantity?.toInt()
                        total_item += total_item1!!

                        val total_price1 = data.getValue(Cart::class.java)?.total?.toInt()
                        total_price += total_price1!!
                    }

                    val adapter = activity.let { CartUserAdapter(cartList,it!!) }
                    rvCart.adapter = adapter
                    adapter.notifyDataSetChanged()

                    totalItem.text = total_item.toString()
                    totalPrice.text = total_price.toString()

                } else {
                    Toast.makeText(activity,"No data Found", Toast.LENGTH_LONG).show()
                }

            }
        })
    }
}
