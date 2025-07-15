package com.micahnyabuto.coinsphere.ui.auth.Signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.micahnyabuto.coinsphere.ui.auth.viewmodel.AuthViewModel
import com.micahnyabuto.coinsphere.ui.navigation.Destinations
import com.micahnyabuto.coinsphere.utils.AppUtil

@Composable
fun SignupScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
){
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


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
        Text("Just a few steps away 😊",
            style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(20.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text("Email address") },
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = { Text("Full name") },
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

        Button(onClick = {
            authViewModel.signUp(email ,name, password){ success ,errorMessage ->
                if (success){
                    navController.navigate(Destinations.Market.route){
                        popUpTo(Destinations.Market.route){inclusive = true}
                    }

                }else{
                    AppUtil.showToast(context, errorMessage?: "Something went wrong")

                }
            }
        },
            modifier = Modifier.fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(6.dp),

        ) {
                Text(
                    text = "Create account",
                    style = MaterialTheme.typography.titleMedium
                )

        }
        Spacer(Modifier.height(20.dp))

        Row {
            TextButton(onClick = {navController.navigate(Destinations.SignIn.route)}) {
                Text("Already have an account?Sign in")
            }
        }
        Spacer(Modifier.height(20.dp))

        TextButton(onClick = {navController.navigate(Destinations.Market.route)}) {
            Text("Skip for now")
        }


    }
}
