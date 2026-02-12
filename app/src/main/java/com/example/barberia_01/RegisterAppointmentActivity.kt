package com.example.barberia_01

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.barberia_01.databinding.ActivityRegisterAppointmentBinding
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
class RegisterAppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterAppointmentBinding
    //con esto inicializamos sin constructor segun el profe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAppointmentBinding.inflate(layoutInflater)
        //es para usar controles sin tener que declararlos
        setContentView(binding.root)

        val services = arrayOf("Corte de Pelo", "Arreglo de Barba", "Afeitado Clásico", "Corte y Barba", "Lavado y Peinado")
        val barbers = arrayOf("Juanito Perez", "Mari Mar", "Carlos Alcantara", "Ana Ramirez")

        val serviceAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, services)
        val barberAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, barbers)


        (binding.tilServicio.editText as? AutoCompleteTextView)?.setAdapter(serviceAdapter)
        (binding.tilBarbero.editText as? AutoCompleteTextView)?.setAdapter(barberAdapter)


        val calendar = Calendar.getInstance()

        binding.etFechaCita.setOnClickListener {
            // Se crea un DatePickerDialog con la fecha actual
            DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    // Cuando se selecciona una fecha, se actualiza el objeto Calendar.
                    calendar.set(year, monthOfYear, dayOfMonth)

                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    binding.etFechaCita.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show() // Muestra el DatePickerDialog.
        }

        binding.etHoraCita.setOnClickListener {
            // Se crea un TimePickerDialog con la hora actual
            TimePickerDialog(
                this,
                { _, hourOfDay, minute ->

                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)

                    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    binding.etHoraCita.setText(timeFormat.format(calendar.time))
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true // true para formato de 24 horas, false para AM/PM.
            ).show() // Muestra el TimePickerDialog.
        }

        //botón |Confirmar cita

        binding.btnConfirmarCita.setOnClickListener {

            val clientName = binding.etNombreCliente.text.toString()
            val clientDNI = binding.etDniCliente.text.toString()
            val clientPhone = binding.etTelefonoCliente.text.toString()
            val service = (binding.tilServicio.editText as? AutoCompleteTextView)?.text.toString()
            val barber = (binding.tilBarbero.editText as? AutoCompleteTextView)?.text.toString()
            val date = binding.etFechaCita.text.toString()
            val time = binding.etHoraCita.text.toString()

            val message = "Cita Confirmada:\n" +
                    "Cliente: $clientName\n" +
                    "DNI: $clientDNI\n" +
                    "Teléfono: $clientPhone\n" +
                    "Servicio: $service\n" +
                    "Barbero: $barber\n" +
                    "Fecha: $date\n" +
                    "Hora: $time"

            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}
