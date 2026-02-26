package com.example.barberia_01

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.barberia_01.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvIr.setOnClickListener {
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }

        binding.btnIngresar.setOnClickListener {
            // Obtenemos el texto de los campos usando el binding
            val username = binding.etUsuario.text.toString().trim()
            val password = binding.etContrasena.text.toString().trim()

            // Validación temporal (estática)
            if (username == "admin" && password == "1234") {
                // Si es correcto, vamos al MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Cerramos el Login para que no se pueda volver atrás con el botón físico
            } else {
                // Si es incorrecto, mostramos un mensaje
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}