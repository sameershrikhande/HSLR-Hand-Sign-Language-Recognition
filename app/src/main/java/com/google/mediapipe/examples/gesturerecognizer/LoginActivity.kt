package com.google.mediapipe.examples.gesturerecognizer
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.mediapipe.examples.gesturerecognizer.MainActivity
import com.google.mediapipe.examples.gesturerecognizer.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Authentication instance
        auth = FirebaseAuth.getInstance()

        // Initialize views
        val editTextUsername = binding.editTextUsername
        val editTextPassword = binding.editTextPassword
        val buttonLogin = binding.buttonLogin
        val textViewRegisterLink = binding.textViewRegisterLink

        // Set click listener for the login button
        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                showToast("Please enter both username and password")
            } else {
                // Call signInWithEmailAndPassword function for Firebase Authentication
                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            showToast("Login Successful")
                            onLoginSuccess()
                        } else {
                            // If sign in fails, display a message to the user.
                            showToast("Authentication failed. Invalid Username or Password")
                        }
                    }
            }
        }

        // Set click listener for the register hyperlink
        textViewRegisterLink.setOnClickListener {
            onRegisterLinkClicked()
        }
    }

    private fun onLoginSuccess() {
        // Start MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        // Finish LoginActivity to prevent user from going back to it after logging in
        finish()
    }

    private fun onRegisterLinkClicked() {
        // Start RegisterActivity
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
