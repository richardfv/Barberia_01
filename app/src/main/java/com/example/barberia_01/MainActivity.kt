package com.example.barberia_01

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.barberia_01.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = Firebase.auth
        db = Firebase.firestore


        val currentUser = auth.currentUser
        
        if (currentUser != null) {

            obtenerPerfilFirestore(currentUser.uid)
        } else {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        configurarNavegacion()
    }

    private fun obtenerPerfilFirestore(userId: String) {

        db.collection("Usuarios").document(userId)
            .get()
            .addOnSuccessListener { document ->

                if (document != null && document.exists()) {
                    val nombre = document.getString("nombre") ?: "Usuario"
                    val rol = document.getString("rol") ?: "Cliente"

                    //una vez que ya lo tenemos llamamos a esta f
                    configurarMenuSegunRol(rol, nombre)
                }
            }
            .addOnFailureListener { e ->

                Toast.makeText(this, "Error al leer perfil: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun configurarMenuSegunRol(rol: String, nombre: String) {

        binding.tvBienvenida.text = "Bienvenido, $nombre"


        if (rol == "Barbero") {
            binding.cvConfigurarDisponibilidad.visibility = View.VISIBLE
            binding.cvGestionarCitasBarbero.visibility = View.VISIBLE
            binding.cvRegistrarCita.visibility = View.GONE
            binding.cvMisCitas.visibility = View.GONE
            binding.cvRealizarPago.visibility = View.GONE
        } else {
            binding.cvConfigurarDisponibilidad.visibility = View.GONE
            binding.cvGestionarCitasBarbero.visibility = View.GONE
            binding.cvRegistrarCita.visibility = View.VISIBLE
            binding.cvMisCitas.visibility = View.VISIBLE
            binding.cvRealizarPago.visibility = View.VISIBLE
        }
        
        binding.cvVerDisponibilidad.visibility = View.VISIBLE
    }

    private fun configurarNavegacion() {

        binding.cvConfigurarDisponibilidad.setOnClickListener {
            val intent = Intent(this, VerDisponibilidadActivity::class.java)
            intent.putExtra("ROL_USUARIO", "Barbero")
            startActivity(intent)
        }

        binding.cvRegistrarCita.setOnClickListener {
            val intent = Intent(this, RegisterAppointmentActivity::class.java)
            startActivity(intent)
        }

        binding.cvVerDisponibilidad.setOnClickListener {
            val intent = Intent(this, VerDisponibilidadActivity::class.java)
            startActivity(intent)
        }

        binding.cvMisCitas.setOnClickListener {
            val intent = Intent(this, MisCitasActivity::class.java)
            startActivity(intent)
        }

        binding.cvRealizarPago.setOnClickListener {
            val intent = Intent(this, PagosActivity::class.java)
            startActivity(intent)
        }
    }
}