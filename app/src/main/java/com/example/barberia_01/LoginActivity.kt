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


        // Aquí obtenemos las referencias a los elementos visuales que creamos en el archivo .xml
        // Usamos 'findViewById' para encontrar cada vista por su 'id' único.
        val usernameEditText = findViewById<TextInputEditText>(R.id.et_username)
        val passwordEditText = findViewById<TextInputEditText>(R.id.et_password)
        val loginButton = findViewById<Button>(R.id.btn_login)


        // 'setOnClickListener' es como decirle a la app: "Oye, cuando alguien haga clic en este botón,
        // ejecuta el código que está aquí dentro".
        loginButton.setOnClickListener {
            // Obtenemos el texto que el usuario escribió y lo convertimos a un String.
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()


            if (username == "admin" && password == "1234") {
                // Si las credenciales son correctas, creamos un 'Intent'.
                // Un 'Intent' es una "intención" de hacer algo. En este caso, la intención
                // es "ir desde esta pantalla (this) a la pantalla MainActivity".
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                // 'finish()' cierra la pantalla de login. De esta manera, si el usuario
                // presiona el botón "atrás" en la pantalla principal, no volverá al login.
                finish()
            } else {
                // Si las credenciales son incorrectas, mostramos un mensaje de error temporal
                // en la parte inferior de la pantalla, llamado 'Toast'.
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
