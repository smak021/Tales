package com.example.tale.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tale.MainActivity
import com.example.tale.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()

        val user = FirebaseAuth.getInstance().currentUser
        if(user!=null)
        {
            signIn()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        createAccountButton.setOnClickListener{

            startActivity(Intent(this, RegisterActivity::class.java))

        }

        loginButton.setOnClickListener{
               FirebaseAuth.getInstance().signInWithEmailAndPassword(UsernameET.text.toString().trim(), passwordET.text.toString().trim())
                   .addOnCompleteListener(this){
                       if(it.isSuccessful){
                           signIn()
                       }
                       else
                       {
                           Toast.makeText(this, "Incorrect Password/Username ", Toast.LENGTH_SHORT).show()
                       }
                   }
            println()
        }
    }

    private fun signIn()
    {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}