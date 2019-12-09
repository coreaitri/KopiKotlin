package com.tugas.kopikuhkopi

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tugas.kopikuhkopi.Admin.AdminBottom.AdminBottomActivity
import com.tugas.kopikuhkopi.User.UserBottom.UserBottomActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(){

    lateinit var animationDrawable: AnimationDrawable

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        animationDrawable = frame.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(3000)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()


        auth = FirebaseAuth.getInstance()


        tv_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java ))
        }

        btn_signin.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin(){
        if(etl_email.text.toString().isEmpty()){
            etl_email.error ="please enter email"
            etl_email.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(etl_email.text.toString()).matches()){
            etl_email.error = "please enter valid email"
            etl_email.requestFocus()
            return
        }

        if(etl_password.text.toString().isEmpty()){
            etl_password.error = " please enter password"
            etl_password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(etl_email.text.toString(), etl_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Login failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser : FirebaseUser?){
        if(currentUser!=null){
            if (currentUser.isEmailVerified){
                if (currentUser.email == "panjaitanfeny@gmail.com"){
                    startActivity(Intent(this, AdminBottomActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(this, UserBottomActivity::class.java))
                    finish()
                }
            } else {
                Toast.makeText(baseContext,"Please verify email address", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(baseContext,"Login Failed", Toast.LENGTH_SHORT).show()
        }
    }
}
