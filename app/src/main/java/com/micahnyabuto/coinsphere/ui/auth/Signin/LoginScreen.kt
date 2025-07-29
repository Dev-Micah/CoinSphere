package com.micahnyabuto.coinsphere.ui.auth.Signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.micahnyabuto.coinsphere.ui.auth.viewmodel.AuthViewModel
import com.micahnyabuto.coinsphere.ui.navigation.Destinations
import com.micahnyabuto.coinsphere.utils.AppUtil
import kotlinx.serialization.builtins.BooleanArraySerializer

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: ()-> Unit
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember {mutableStateOf(false)}
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxWidth()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 150.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Welcome back 👋",
            style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text("Email address") },
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = {
                isLoading = true
                authViewModel.login(email, password) { success, error ->
                    isLoading = false
                    if (success) {
                        onLoginSuccess()
                    } else {
                        errorMessage = error
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
                               .padding(4.dp),
            shape = RoundedCornerShape(6.dp)
        ) {
            if (isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp))
            else Text("Sign In")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
        Spacer(Modifier.height(20.dp))
        TextButton(onClick = {
            navController.navigate(Destinations.SignUp.route)
        }) {
            Text("Don't have an account? Sign Up")
        }

    }
}
