package com.micahnyabuto.coinsphere.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.micahnyabuto.coinsphere.data.remote.user.User
import jakarta.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine


class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> =
        suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(Result.success(Unit), null)
                    } else {
                        cont.resume(Result.failure(task.exception ?: Exception("Login failed")), null)
                    }
                }
        }

    override suspend fun signUp(email: String, name: String, password: String): Result<Unit> =
        suspendCancellableCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = task.result?.user?.uid.orEmpty()
                        val user = User(name, email, uid)

                        firestore.collection("users")
                            .document(uid)
                            .set(user)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    cont.resume(Result.success(Unit), null)
                                } else {
                                    cont.resume(Result.failure(dbTask.exception ?: Exception("Saving user failed")), null)
                                }
                            }
                    } else {
                        cont.resume(Result.failure(task.exception ?: Exception("Signup failed")), null)
                    }
                }
        }
}
