package com.example.coincapapp.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp





// 1. La UI básica sin lógica pesada
@Composable
fun SettingsView(
    isUserLoggedIn: Boolean,
    userEmail: String?,
    onLoginClicked: (String, String) -> Unit,
    onLogoutClicked: () -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text("Settings", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))

        if (!isUserLoggedIn) {
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onLoginClicked(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        } else {
            Text("Logged in as: $userEmail")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onLogoutClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
        }
    }
}

// 2. Conexión con Firebase REAL para correr la app
@Composable
fun SettingsScreen() {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    SettingsView(
        isUserLoggedIn = currentUser != null,
        userEmail = currentUser?.email,
        onLoginClicked = { emailInput, passwordInput ->
            auth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        },
        onLogoutClicked = {
            auth.signOut()
            Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
        },
        email = email,
        onEmailChange = { email = it },
        password = password,
        onPasswordChange = { password = it }
    )
}

// 3. Ahora el Preview es seguro
@Preview(showBackground = true)
@Composable
fun SettingsViewPreview() {
    SettingsView(
        isUserLoggedIn = false, // Simular no logueado
        userEmail = null,
        onLoginClicked = { _, _ -> },
        onLogoutClicked = {},
        email = "",
        onEmailChange = {},
        password = "",
        onPasswordChange = {}
    )
}





