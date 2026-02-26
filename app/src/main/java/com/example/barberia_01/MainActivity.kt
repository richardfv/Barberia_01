package com.example.barberia_01

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.barberia_01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- PRUEBA DE ROLES ---
        val rolUsuario = "Cliente"
        val nombreUsuario = "Richi"

        // Llamamos a nuestra función para configurar lo que el usuario puede ver
        configurarMenuSegunRol(rolUsuario, nombreUsuario)

        configurarNavegacion()
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

            // opciones del Cliente
            binding.cvRegistrarCita.visibility = View.VISIBLE
            binding.cvMisCitas.visibility = View.VISIBLE
            binding.cvRealizarPago.visibility = View.VISIBLE

            binding.cvConfigurarDisponibilidad.visibility = View.GONE
            binding.cvGestionarCitasBarbero.visibility = View.GONE
        }
        
        //ambos roles
        binding.cvVerDisponibilidad.visibility = View.VISIBLE
    }

    private fun configurarNavegacion() {
        // Acción: Registrar Cita
        binding.cvRegistrarCita.setOnClickListener {
            val intent = Intent(this, RegisterAppointmentActivity::class.java)
            startActivity(intent)
        }

        // Acción: Ver Disponibilidad
        binding.cvVerDisponibilidad.setOnClickListener {
            val intent = Intent(this, VerDisponibilidadActivity::class.java)
            startActivity(intent)
        }

        // Acción: Mis Citas
        binding.cvMisCitas.setOnClickListener {
            val intent = Intent(this, MisCitasActivity::class.java)
            startActivity(intent)
        }

        // Acción: Pagos
        binding.cvRealizarPago.setOnClickListener {
            val intent = Intent(this, PagosActivity::class.java)
            startActivity(intent)
        }

    }
}