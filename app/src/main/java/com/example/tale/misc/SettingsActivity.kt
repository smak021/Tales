package com.example.tale.misc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tale.R
import com.example.tale.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)



        logoutButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            finish()

            startActivity(Intent(this, LoginActivity::class.java))

        }
    }
}