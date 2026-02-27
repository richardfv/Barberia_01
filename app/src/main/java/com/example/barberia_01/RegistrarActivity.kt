package com.example.barberia_01

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.barberia_01.databinding.ActivityRegistrarBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class RegistrarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        auth = Firebase.auth 
        db = Firebase.firestore 

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnRegistrar.setOnClickListener {
            registrarUsuarioFirebase()
        }

        binding.tvVolverLogin.setOnClickListener {
            finish()
        }
    }

    private fun registrarUsuarioFirebase() {
        val nombre = binding.etUsuario.text.toString().trim()
        val correo = binding.etCorreo.text.toString().trim()
        val contra = binding.etContrasena.text.toString().trim()
        val contra2 = binding.etContrasena2.text.toString().trim()

        val selectedRolId = binding.rgRol.checkedRadioButtonId
        val rol = if (selectedRolId == R.id.rbCliente) "Cliente" else "Barbero"

        if (nombre.isEmpty() || correo.isEmpty() || contra.isEmpty() || contra2.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        if (contra != contra2) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }
        if (contra.length < 6) {
            Toast.makeText(this, "La contraseña debe ser de al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return
        }

        // --- registro cargando ---
        binding.pbRegistro.visibility = View.VISIBLE
        binding.btnRegistrar.isEnabled = false

        // Crear usuario en Auth
        auth.createUserWithEmailAndPassword(correo, contra)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    guardarPerfilUsuario(userId, nombre, correo, rol)
                } else {
                    // fin de cargando (fallo en el registro)
                    binding.pbRegistro.visibility = View.GONE
                    binding.btnRegistrar.isEnabled = true
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun guardarPerfilUsuario(id: String?, nombre: String, correo: String, rol: String) {
        if (id == null) {
            binding.pbRegistro.visibility = View.GONE
            binding.btnRegistrar.isEnabled = true
            return
        }

        val userProfile = hashMapOf(
            "nombre" to nombre,
            "correo" to correo,
            "rol" to rol,
            "uid" to id
        )

        // Guardar en Firestore
        db.collection("Usuarios").document(id)
            .set(userProfile)
            .addOnSuccessListener {
                Toast.makeText(this, "¡Registro Exitoso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                // --- FIN DE CARGA (SI FALLA EL GUARDADO DEL PERFIL) ---
                binding.pbRegistro.visibility = View.GONE
                binding.btnRegistrar.isEnabled = true
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}