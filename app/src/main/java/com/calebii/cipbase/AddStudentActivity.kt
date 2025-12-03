package com.calebii.cipbase

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.calebii.cipbase.databinding.ActivityAddStudentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStudentBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Agregar estudiante"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        binding.btnGuardar.setOnClickListener {
            saveStudent()
        }

        val etfecha = binding.etFecha

        binding.etFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val año = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val día = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this, // contexto
                { _, year, month, dayOfMonth ->
                    val fechaSeleccionada = "%02d/%02d/%d".format(dayOfMonth, month + 1, year)
                    etfecha.setText(fechaSeleccionada)
                },
                año, mes, día
            )

            datePicker.datePicker.maxDate = System.currentTimeMillis()

            datePicker.show()
        }


    }

    private fun saveStudent(){

        val name = binding.etNombre.text.toString()
        val email = binding.etCorreo.text.toString()
        val phone = binding.etTelefono.text.toString()
        val matricula = binding.etMatricula.text.toString()
        val edad = binding.etedad.text.toString()
        val fecha = binding.etFecha.text.toString()
        val curp = binding.etCurp.text.toString()



        if (name.isEmpty() || phone.isEmpty() || matricula.isEmpty() || edad.isEmpty()){
            binding.etedad.error = "Este campo es obligatorio"
            binding.etTelefono.error = "Este campo es obligatorio"
            binding.etMatricula.error = "Este campo es obligatorio"
            binding.etNombre.error = "Este campo es obligatorio"
            return
        }
        binding.btnGuardar.isEnabled = false
        val student = Student(name, email, phone, "Activo", matricula, edad, fecha,curp)
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        db.collection("usuarios").document(uid).collection("students")
            .add(student)
            .addOnSuccessListener {
                Toast.makeText(this, "Estudiante agregado", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK) // opcional indicar éxito
                finish()
            }
            .addOnFailureListener { e ->
                binding.btnGuardar.isEnabled = true
                Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
            }


        }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    }


