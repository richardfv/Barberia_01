package com.example.barberia_01



import android.os.Bundle
import android.content.Intent
import android.app.Activity
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

    // Cuando VerDisponibilidadActivity nos devuelva
    // un resultado, usaremos este código para saber que es la respuesta a nuestra petición.
    private val CODIGO_SOLICITUD_FECHA_HORA = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAppointmentBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val services = arrayOf("Corte de Pelo", "Arreglo de Barba", "Afeitado Clásico", "Corte y Barba", "Lavado y Peinado")
        val barbers = arrayOf("Juanito Perez", "Mari Mar", "Carlos Alcantara", "Ana Ramirez")

        val serviceAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, services)
        val barberAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, barbers)


        (binding.tilServicio.editText as? AutoCompleteTextView)?.setAdapter(serviceAdapter)
        (binding.tilBarbero.editText as? AutoCompleteTextView)?.setAdapter(barberAdapter)

        //"Elegir"
        binding.btnBuscarDisponibilidad.setOnClickListener {

            val intent = Intent(this, VerDisponibilidadActivity::class.java)

            // En lugar de startActivity, usamos startActivityForResult. para esperar un resultado de vuelta
            startActivityForResult(intent, CODIGO_SOLICITUD_FECHA_HORA)
        }


        binding.btnConfirmarCita.setOnClickListener {

            val clientName = binding.etNombreCliente.text.toString()
            val clientDNI = binding.etDniCliente.text.toString()
            val clientPhone = binding.etTelefonoCliente.text.toString()
            val service = (binding.tilServicio.editText as? AutoCompleteTextView)?.text.toString()
            val barber = (binding.tilBarbero.editText as? AutoCompleteTextView)?.text.toString()
            val fechaHoraSeleccionada = binding.tvFechaHoraSeleccionada.text.toString()


            val message = "Cita Confirmada:\n" +
                    "Cliente: $clientName\n" +
                    "DNI: $clientDNI\n" +
                    "Teléfono: $clientPhone\n" +
                    "Servicio: $service\n" +
                    "Barbero: $barber\n" +
                    "Fecha y Hora: $fechaHoraSeleccionada"

            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    // función especial de Android que se llama automáticamente
    // cuando una actividad que lanzamos con startActivityForResult termina y nos devuelve un resultado.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Primero, comprobamos que el resultado que estamos recibiendo es para la petición que hicimos.
        // Comparamos el requestCode con el código que enviamos (CODIGO_SOLICITUD_FECHA_HORA).
        if (requestCode == CODIGO_SOLICITUD_FECHA_HORA) {

            // Activity.RESULT_OK es una constante de Android que indica que todo fue bien.
            if (resultCode == Activity.RESULT_OK) {

                // Si todo fue bien, extraemos los datos que la otra actividad nos envió en el Intent.
                // Usamos la misma clave ("FECHA_SELECCIONADA", "HORA_SELECCIONADA") que usamos para enviarlos.
                val fechaSeleccionada = data?.getStringExtra("FECHA_SELECCIONADA")
                val horaSeleccionada = data?.getStringExtra("HORA_SELECCIONADA")

                // datos no son nulos o vacíos por seguridad
                if (!fechaSeleccionada.isNullOrEmpty() && !horaSeleccionada.isNullOrEmpty()) {
                    // Si tenemos los datos, actualizamos nuestro TextView para que el usuario los vea.
                    binding.tvFechaHoraSeleccionada.text = "$fechaSeleccionada, $horaSeleccionada"
                } else {
                    // Si algo falló al recibir los datos, lo indicamos.
                    binding.tvFechaHoraSeleccionada.text = "Error al seleccionar"
                }
            }
        }
    }
}
