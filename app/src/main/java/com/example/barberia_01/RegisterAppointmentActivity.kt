package com.example.barberia_01

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.barberia_01.databinding.ActivityRegisterAppointmentBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class RegisterAppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterAppointmentBinding
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    // Listas para manejar barberos
    private val listaNombresBarberos = mutableListOf<String>()
    private val listaIdsBarberos = mutableListOf<String>()
    private var barberoIdSeleccionado: String = ""

    private val CODIGO_SOLICITUD_FECHA_HORA = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val services = arrayOf("Corte de Pelo", "Arreglo de Barba", "Afeitado Clásico", "Corte y Barba", "Lavado y Peinado")
        val serviceAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, services)
        (binding.tilServicio.editText as? AutoCompleteTextView)?.setAdapter(serviceAdapter)


        cargarBarberosReales()

        binding.btnBuscarDisponibilidad.setOnClickListener {
            //se obtiene el texto del autocpl
            val nombreBarbero = (binding.tilBarbero.editText as? AutoCompleteTextView)?.text.toString()
            
            //1ra ocurrencia
            val index = listaNombresBarberos.indexOf(nombreBarbero)
            
            if (index == -1) {
                Toast.makeText(this, "Por favor, selecciona un barbero de la lista", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //obtenemos el id en frre
            barberoIdSeleccionado = listaIdsBarberos[index]

            val intent = Intent(this, VerDisponibilidadActivity::class.java)
            intent.putExtra("ROL_USUARIO", "Cliente")
            intent.putExtra("BARBERO_ID", barberoIdSeleccionado)
            intent.putExtra("BARBERO_NOMBRE", nombreBarbero)
            startActivityForResult(intent, CODIGO_SOLICITUD_FECHA_HORA)
        }

        binding.btnConfirmarCita.setOnClickListener {
            guardarCitaEnFirestore()
        }
    }

    private fun cargarBarberosReales() {
        db.collection("Usuarios")
            .whereEqualTo("rol", "Barbero")
            .get()
            .addOnSuccessListener { documents ->
                listaNombresBarberos.clear()
                listaIdsBarberos.clear()
                
                for (doc in documents) {
                    val nombre = doc.getString("nombre") ?: "Barbero"
                    val id = doc.id
                    
                    listaNombresBarberos.add(nombre)
                    listaIdsBarberos.add(id)
                }

                // nuevo adapte Actualizamos
                val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listaNombresBarberos)
                (binding.tilBarbero.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al cargar barberos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun guardarCitaEnFirestore() {
        //recopilamos la info
        val nombre = binding.etNombreCliente.text.toString().trim()
        val dni = binding.etDniCliente.text.toString().trim()
        val telefono = binding.etTelefonoCliente.text.toString().trim()
        val servicio = (binding.tilServicio.editText as? AutoCompleteTextView)?.text.toString()
        val barbero = (binding.tilBarbero.editText as? AutoCompleteTextView)?.text.toString()
        val fechaHora = binding.tvFechaHoraSeleccionada.text.toString()

        if (nombre.isEmpty() || dni.isEmpty() || telefono.isEmpty() || 
            servicio.isEmpty() || barbero.isEmpty() || fechaHora == "Sin seleccionar") {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        //cosntr objda fiste
        val cita = hashMapOf(
            "clienteId" to (auth.currentUser?.uid ?: ""),
            "nombreCliente" to nombre,
            "dniCliente" to dni,
            "telefonoCliente" to telefono,
            "servicio" to servicio,
            "barbero" to barbero,
            "barberoId" to barberoIdSeleccionado,
            "fechaHora" to fechaHora,
            "estado" to "Pendiente"
        )

        db.collection("Citas").add(cita)
            .addOnSuccessListener {
                Toast.makeText(this, "¡Cita agendada!", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al guardar cita: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //resultado y operacion exitosa dauth
        if (requestCode == CODIGO_SOLICITUD_FECHA_HORA && resultCode == Activity.RESULT_OK) {
            val fecha = data?.getStringExtra("FECHA_SELECCIONADA")
            val hora = data?.getStringExtra("HORA_SELECCIONADA")
            if (!fecha.isNullOrEmpty() && !hora.isNullOrEmpty()) {
                binding.tvFechaHoraSeleccionada.text = "$fecha, $hora"
            }
        }
    }
}