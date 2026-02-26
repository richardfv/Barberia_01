package com.example.barberia_01

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.barberia_01.databinding.ActivityRegistrarBinding

class RegistrarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

                binding.btnRegistrar.setOnClickListener {
                    registrarUsuario()
                }
        
                binding.tvVolverLogin.setOnClickListener {
                    finish()
                }
        
            }

    private fun registrarUsuario() {
        // Accedemos a los campos
        val usuario = binding.etUsuario.text.toString().trim()
        val correo = binding.etCorreo.text.toString().trim()
        val contra = binding.etContrasena.text.toString().trim()
        val contra2 = binding.etContrasena2.text.toString().trim()

        // Obtenemos el ID del RadioButton seleccionado
        val selectedRolId = binding.rgRol.checkedRadioButtonId
        val rol = if (selectedRolId == R.id.rbCliente) "Cliente" else "Barbero"

        // Validación simple
        if (usuario.isEmpty() || correo.isEmpty() || contra.isEmpty() || contra2.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (contra != contra2) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        // Mensaje de prueba
        Toast.makeText(this, "Registrando a $usuario como $rol...", Toast.LENGTH_LONG).show()
    }
}