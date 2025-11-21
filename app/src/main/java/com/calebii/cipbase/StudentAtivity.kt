package com.calebii.cipbase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.calebii.cipbase.databinding.ActivityStudentAtivityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StudentAtivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentAtivityBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var adapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentAtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

         // Menu de opciones
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_students -> {
                    true // Ya estás aquí
                }

                R.id.nav_reports -> {
                    supportActionBar?.title = "Informes"
                    startActivity(Intent(this@StudentAtivity, ReportActivity::class.java))
                    true
                }
                R.id.nav_home -> {
                    supportActionBar?.title = "Inicio"
                    startActivity(Intent(this@StudentAtivity, HomeActivity::class.java))
                    true
                }
                R.id.nav_payments -> {
                    supportActionBar?.title = "Pagos"
                    startActivity(Intent(this@StudentAtivity, PayActivity::class.java))
                    true
                }
                else -> false
            }
        }
        // Fin del menu de opciones

        // Configurar RecyclerView
        adapter = StudentAdapter(studentList)
        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)
        binding.recyclerStudents.adapter = adapter
        // Fin de la configuracion

        //Boton de agregar estudiantes
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this@StudentAtivity, AddStudentActivity::class.java)
            startActivity(intent)
        }
        // Fin del boton de agregar estudiantes

    }
    override fun onResume() {
        super.onResume()
        loadStudents()
    }


    private fun loadStudents() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("usuarios")
            .document(uid)
            .collection("students")
            .get()
            .addOnSuccessListener { result ->
                studentList.clear()
                for (document in result) {
                    val student = document.toObject(Student::class.java)
                    studentList.add(student)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar los datos", Toast.LENGTH_SHORT).show()
            }
    }
}