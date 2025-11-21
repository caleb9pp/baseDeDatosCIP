package com.calebii.cipbase

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class PayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav1)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_payments -> {
                    true // Ya estás aquí
                }

                R.id.nav_students -> {
                    supportActionBar?.title = "Estudiantes"
                    startActivity(Intent(this@PayActivity, StudentAtivity::class.java))
                    true
                }
                R.id.nav_home -> {
                    supportActionBar?.title = "Inicio"
                    startActivity(Intent(this@PayActivity, HomeActivity::class.java))
                    true
                }
                R.id.nav_reports -> {
                    supportActionBar?.title = "Informes"
                    startActivity(Intent(this@PayActivity, ReportActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}