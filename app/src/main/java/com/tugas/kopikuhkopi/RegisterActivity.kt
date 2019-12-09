package com.tugas.kopikuhkopi

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(){
    lateinit var animationDrawable: AnimationDrawable

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        animationDrawable = frame.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(3000)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()

        auth = FirebaseAuth.getInstance()

        btn_signup.setOnClickListener {
            signupUser()
        }
    }

    fun signupUser(){

        if(etr_name.text.toString().isEmpty()){
            etr_name.error = "please enter name"
            etr_name.requestFocus()
            return
        }

        if(etr_email.text.toString().isEmpty()){
            etr_email.error ="please enter email"
            etr_email.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(etr_email.text.toString()).matches()){
            etr_email.error = "please enter valid email"
            etr_email.requestFocus()
            return
        }

        if(etr_password.text.toString().isEmpty()){
            etr_password.error = " please enter password"
            etr_password.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(etr_email.text.toString(), etr_password.text.toString())
            .addOnCompleteListener(this) { task ->
                val user = auth.currentUser
                if (task.isSuccessful) {
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, LoginActivity::class.java ))
                                finish()
                            }
                        }

                } else {
                    Toast.makeText(baseContext, "Register failed.",
                        Toast.LENGTH_SHORT).show()
                }

            }
    }
}