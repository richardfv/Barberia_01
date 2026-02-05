package com.example.barberia_01

import android.os.Bundle
import android.content.Intent
import com.google.android.material.card.MaterialCardView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cardRegisterAppointment = findViewById<MaterialCardView>(R.id.card_register_appointment)

        cardRegisterAppointment.setOnClickListener {
            val intent = Intent(this, RegisterAppointmentActivity::class.java)
            startActivity(intent)
        }


        }
    }