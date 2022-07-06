package com.example.tale.auth

import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tale.MainActivity
import com.example.tale.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    val user = FirebaseAuth.getInstance().currentUser
    override fun onStart() {
        super.onStart()


        if(user!=null)
        {
            signIn()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (user == null) {
            val loginButton: Button = findViewById(R.id.loginButton)
            createAccountButton.setOnClickListener {

                startActivity(Intent(this, RegisterActivity::class.java))


            }

            loginButton.setOnClickListener {
                if (UsernameET.text.toString().trim() == "" || passwordET.text.toString()
                        .trim() == ""
                ) {
                    Toast.makeText(
                        this,
                        "Please enter a valid username/password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        UsernameET.text.toString().trim(),
                        passwordET.text.toString().trim()
                    )
                        .addOnCompleteListener(this) {
                            if (it.isSuccessful) {
                                signIn()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Incorrect Password/Username ",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    println()
                }
            }
        }
    }

    private fun signIn()
    {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}

