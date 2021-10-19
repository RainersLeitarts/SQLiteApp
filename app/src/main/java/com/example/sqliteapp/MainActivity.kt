package com.example.sqliteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameText = findViewById<EditText>(R.id.name_text)
        val surnameText = findViewById<EditText>(R.id.surname_text)
        val nicknameText = findViewById<EditText>(R.id.nickname_text)
        val password1 = findViewById<EditText>(R.id.password_text)
        val password2 = findViewById<EditText>(R.id.reenter_password_text)
        val registerBtn = findViewById<Button>(R.id.register_btn)
        val loginBtn = findViewById<Button>(R.id.register_login_button)

        registerBtn.setOnClickListener {
            if(nameText.text.toString().isNotEmpty() && surnameText.text.toString().isNotEmpty() && nicknameText.text.toString().isNotEmpty() && password1.text.toString().isNotEmpty()){
                if (password1.text.toString() == password2.text.toString()){
                    val user = User(nameText.text.toString(), surnameText.text.toString(), nicknameText.text.toString(), password1.text.toString())
                    val db = DataBaseHandler(this)
                    db.insertData(user)
                    Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }

        }

        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            finish()
            overridePendingTransition(0,0)
            startActivity(intent)
        }

    }
}