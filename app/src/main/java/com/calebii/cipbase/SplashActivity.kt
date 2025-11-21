package com.calebii.cipbase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // splash screen
        android.os.Handler().postDelayed({
            val user = FirebaseAuth.getInstance().currentUser

            if (user != null) {
                // Usuario logueado → Ir a HomeActivity
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                // No logueado → Ir a AuthActivity
                startActivity(Intent(this, AuthActivity::class.java))
            }

            finish() // Cerrar Splash para que no pueda volver atrás
        }, 1000)
    }
}