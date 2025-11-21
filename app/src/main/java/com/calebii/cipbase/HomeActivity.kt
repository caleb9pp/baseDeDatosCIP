package com.calebii.cipbase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Referencias UI
        val textBienvenida = findViewById<TextView>(R.id.textViewBienvenida)
        val btnCerrar = findViewById<Button>(R.id.btnCerrar)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        // Obtener usuario actual
        val user = auth.currentUser

        if (user != null) {
            // Leer datos desde Firestore
            db.collection("usuarios").document(user.uid).get()
                .addOnSuccessListener { documento ->
                    if (documento.exists()) {
                        val sucursal = documento.getString("sucursal")
                        textBienvenida.text = "Bienvenido  \nSucursal: $sucursal"
                    } else {
                        textBienvenida.text = "Datos no encontrados"
                    }
                }
                .addOnFailureListener {
                    textBienvenida.text = "Error al cargar datos"
                }
        }

        btnCerrar.setOnClickListener {
            // 1. Cerrar Firebase
            auth.signOut()

            // 2. Cerrar Google
            val googleSignInClient = GoogleSignIn.getClient(
                this,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            )

            googleSignInClient.signOut().addOnCompleteListener {
                // 3. Redirigir al login y limpiar historial
                val intent = Intent(this, AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    true // Ya estás aquí
                }
                R.id.nav_students -> {
                    startActivity(Intent(this@HomeActivity, StudentAtivity::class.java))
                    supportActionBar?.title = "Estudiantes"
                    true
                }
                R.id.nav_payments -> {
                    startActivity(Intent(this@HomeActivity, PayActivity::class.java))
                    supportActionBar?.title = "Pagos"
                    true
                }
                R.id.nav_reports -> {
                    startActivity(Intent(this@HomeActivity, ReportActivity::class.java))
                    supportActionBar?.title = "Informes"
                    true
                }
                else -> false
            }
        }

    }
}