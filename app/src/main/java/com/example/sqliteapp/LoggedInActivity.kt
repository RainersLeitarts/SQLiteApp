package com.example.sqliteapp

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class LoggedInActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        //hides action bar
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)

        val profileIcon = findViewById<ImageView>(R.id.profile_icon)
        val idTextView = findViewById<TextView>(R.id.id_textview)
        val nameTextView = findViewById<TextView>(R.id.name_textview)
        val surnameTextView = findViewById<TextView>(R.id.surname_textview)
        val usernameTextView = findViewById<TextView>(R.id.username_textview)
        val passwordTextView = findViewById<TextView>(R.id.password_textview)
        var showingInfo: Boolean = false

        profileIcon.setOnClickListener{
            val db = DataBaseHandler(this)
            val AESCrypt = AESCrypt()

            val bundle: Bundle? = intent.extras

            val username: String = bundle?.getString("loggedInUser").toString()

            val user = db.getUserData(username)

            //1st cheap way to hide info
            if (showingInfo){
                idTextView.text = ""
                nameTextView.text = ""
                surnameTextView.text = ""
                usernameTextView.text = ""
                passwordTextView.text = ""
                showingInfo = false
            //if user != null display data
            }else if(user != null) {
                val decryptedPassword = AESCrypt.decrypt(user.password)
                idTextView.text = "ID: ${user.id}"
                nameTextView.text = "NAME: ${user.name}"
                surnameTextView.text = "SURNAME: ${user.surname}"
                usernameTextView.text = "USERNAME: ${user.nickName}"
                passwordTextView.text = "PASSWORD: $decryptedPassword"
                showingInfo = true
            }
        }
    }
}