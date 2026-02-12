package com.example.barberia_01

import android.os.Bundle
import android.content.Intent
import android.widget.Toast // Importar la clase Toast
import com.google.android.material.card.MaterialCardView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val cardRegistrarCita = findViewById<MaterialCardView>(R.id.cvRegistrarCita)
        val cardVerDisponibilidadBarberos = findViewById<MaterialCardView>(R.id.cvVerDisponibilidad)
        val cardMisCitas = findViewById<MaterialCardView>(R.id.cvMisCitas)
        val cardRealizarPago = findViewById<MaterialCardView>(R.id.cvRealizarPago)

        //"Registrar Cita"
        cardRegistrarCita.setOnClickListener {
            val intent = Intent(this, RegisterAppointmentActivity::class.java)
            startActivity(intent)
        }

        //"Ver Disponibilidad de Barberos"
        cardVerDisponibilidadBarberos.setOnClickListener {
            val intent = Intent(this, VerDisponibilidadActivity::class.java)
            startActivity(intent)
        }

        //"Mis Citas"
        cardMisCitas.setOnClickListener {
            val intent = Intent(this, MisCitasActivity::class.java)
            startActivity(intent)
        }

        //"Pagos"
        cardRealizarPago.setOnClickListener {
            val intent = Intent(this, PagosActivity::class.java)
            startActivity(intent)
        }
    }
}