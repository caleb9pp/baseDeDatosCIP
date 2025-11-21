package com.calebii.cipbase

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_reports -> {
                    true // Ya estás aquí
                }

                R.id.nav_students -> {
                    supportActionBar?.title = "Estudiantes"
                    startActivity(Intent(this@ReportActivity, StudentAtivity::class.java))
                    true
                }
                R.id.nav_home -> {
                    supportActionBar?.title = "Inicio"
                    startActivity(Intent(this@ReportActivity, HomeActivity::class.java))
                    true
                }
                R.id.nav_payments -> {
                    supportActionBar?.title = "Pagos"
                    startActivity(Intent(this@ReportActivity, PayActivity::class.java))
                    true
                }
                else -> false
            }
        }

    }
}