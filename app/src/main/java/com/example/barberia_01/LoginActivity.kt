package com.example.barberia_01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val usernameEditText = findViewById<TextInputEditText>(R.id.etUsuario)
        val passwordEditText = findViewById<TextInputEditText>(R.id.etContrasena)
        val loginButton = findViewById<Button>(R.id.btnIngresar)
        val tvIrRegistro = findViewById<android.widget.TextView>(R.id.tvIr)


        tvIrRegistro.setOnClickListener {
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            // Obtenemos el texto que el usuario escribió y lo convertimos a un String.
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()


            if (username == "admin" && password == "1234") {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)


                finish()
            } else {

                Toast.makeText(this, "Usuario o contraseña incorrectos",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}
