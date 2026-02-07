package com.example.barberia_01

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterAppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_appointment)

        val buttonConfirmAppointment = findViewById<Button>(R.id.buttonConfirmAppointment)

        buttonConfirmAppointment.setOnClickListener {
            Toast.makeText(this, "AUN NO CREO ESTA PANTALLA XD", Toast.LENGTH_SHORT).show()
    }

    }
}
