package com.tugas.kopikuhkopi.User

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.tugas.kopikuhkopi.LoginActivity
import com.tugas.kopikuhkopi.R
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        auth = FirebaseAuth.getInstance()

        btn_change_password.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword(){
        if(etc_currentP.text.isNotEmpty() &&
            etc_newP.text.isNotEmpty() &&
            etc_confNewP.text.isNotEmpty()){
            if(etc_newP.text.toString().equals(etc_confNewP.text.toString())) {

                val user = auth.currentUser
                if (user != null && user.email !=null){
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, etc_currentP.text.toString())

                    user.reauthenticate(credential)
                        ?.addOnCompleteListener {

                            if(it.isSuccessful){
                                Toast.makeText(this, "Re-Authentication success", Toast.LENGTH_SHORT).show()

                                user.updatePassword(etc_newP.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(this, "password changed successfully", Toast.LENGTH_SHORT).show()
                                            auth.signOut()
                                            startActivity(Intent(this, LoginActivity::class.java))
                                            finish()
                                        }
                                    }

                            } else {
                                Toast.makeText(this, "Re-Authentication failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }else {
                Toast.makeText(this, "Password Mismatching", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "please enter all the fields", Toast.LENGTH_SHORT).show()
        }
    }
}