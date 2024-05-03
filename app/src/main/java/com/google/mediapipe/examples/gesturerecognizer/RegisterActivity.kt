package com.google.mediapipe.examples.gesturerecognizer
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.mediapipe.examples.gesturerecognizer.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerbinding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerbinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerbinding.root)

        // Initialize Firebase Authentication instance
        auth = FirebaseAuth.getInstance()

        // Initialize views
        val nameEditText = registerbinding.editTextName
        val usernameEditText = registerbinding.editTextUsername
        val emailEditText = registerbinding.editTextEmail
        val passwordEditText = registerbinding.editTextPassword
        val confirmPasswordEditText = registerbinding.editTextConfirmPassword
        val registerButton = registerbinding.buttonRegister

        // Set click listener for register button
        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // Perform validation
            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showToast("Please fill in all fields")
            } else if (password != confirmPassword) {
                showToast("Passwords do not match")
            } else {
                // Call createUserWithEmailAndPassword function for Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registration success
                            showToast("Registration successful")
                            // Navigate to LoginActivity or any other activity after successful registration
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish() // Finish RegisterActivity to prevent user from going back to it after registration
                        } else {
                            // If registration fails, display a message to the user.
                            showToast("Registration failed: ${task.exception?.message}")
                        }
                    }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
