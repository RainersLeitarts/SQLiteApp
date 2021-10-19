package com.example.sqliteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBtn = findViewById<Button>(R.id.login_login_button)
        val registerBtn = findViewById<Button>(R.id.login_register_button)
        val usernameText = findViewById<EditText>(R.id.login_username_text)
        val passwordText = findViewById<EditText>(R.id.login_password_text)

        loginBtn.setOnClickListener {
            val username = usernameText.text.toString()
            val password = passwordText.text.toString()
            val db = DataBaseHandler(this)

            if (db.checkLogin(username, password)){
                val intent = Intent(this, LoggedInActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Enter correct credentials", Toast.LENGTH_LONG).show()
            }

        }

        registerBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            finish()
            overridePendingTransition(0,0)
            startActivity(intent)
        }
    }
}