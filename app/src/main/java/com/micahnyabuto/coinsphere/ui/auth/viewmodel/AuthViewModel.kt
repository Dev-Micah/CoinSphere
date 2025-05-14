package com.micahnyabuto.coinsphere.ui.auth.viewmodel

import android.R
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.micahnyabuto.coinsphere.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class AuthViewModel@Inject constructor() : ViewModel() {

    private val auth = Firebase.auth

    private val firestore = Firebase.firestore

    fun login(email: String,password: String,onResult: (Boolean, String?) -> Unit){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    onResult(true, null)

                }else{
                    onResult(false,it.exception?.localizedMessage)
                }
            }

    }
    fun signUp(email : String, name: String ,password : String, onResult: (Boolean, String?) -> Unit){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful ){
                    var userId = it.result?.user?.uid
                    val user = User(name, email, userId!!)
                    firestore.collection("users").document(userId)
                        .set(user)
                        .addOnCompleteListener { dbTask->
                            if (dbTask.isSuccessful){
                                onResult(true, null)
                            }else{
                                onResult(false, "Something went wrong")
                            }

                        }

                }else{
                    onResult(false,it.exception?.localizedMessage)
                }

            }

    }
}