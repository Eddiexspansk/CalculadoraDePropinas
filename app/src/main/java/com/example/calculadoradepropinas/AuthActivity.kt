package com.example.calculadoradepropinas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.calculadoradepropinas.databinding.ActivityAuthBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth



class AuthActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityAuthBinding
    private lateinit var viewBinding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAuthBinding.inflate(this.layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        val passwordEditText = viewBinding.passwordEditText
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion Firebase completa")
        analytics.logEvent("InitScreen", bundle)
        createAccount(findViewById(R.id.emailEditText), passwordEditText)
        signInAccount(findViewById(R.id.emailEditText), passwordEditText)
    }

    private fun createAccount(emailEditText: EditText, passwordEditText: EditText) {
        title = "Autenticación"
        val auth = FirebaseAuth.getInstance()
        val signUpButton = viewBinding.signUpButton
        signUpButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        showHome(user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }

    private fun signInAccount(emailEditText: EditText, passwordEditText: EditText) {
        title = "Acceso"
        val auth = FirebaseAuth.getInstance()
        val logInButton = viewBinding.loginButton
        logInButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                auth.signInWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        showHome(user?.email ?: "", ProviderType.BASIC)
                        
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("error")
        builder.setMessage("Se ha producido un error en la autenticación")
        builder.setPositiveButton("Aceptar", null)
        val dialog:AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(emailEditText:String, provider:ProviderType) {
        val calcIntent = Intent(this, CalcActivity::class.java).apply {
            putExtra("email", emailEditText)
            putExtra("provider",provider.name)
        }
        startActivity(calcIntent)
    }

}