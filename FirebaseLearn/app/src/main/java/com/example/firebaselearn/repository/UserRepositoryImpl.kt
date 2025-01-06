package com.example.firebaselearn.repository

import com.example.firebaselearn.ui.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserRepositoryImpl : UserRepository {
    private var auth: FirebaseAuth =FirebaseAuth.getInstance()

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference =database.reference.child("users") //database.reference = root // child= new dir/table
    override fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{ //calling login func
            if (it.isSuccessful){
                callback(true,"Login Success")
        }else{
            callback(false,it.exception?.message.toString()) //passes error messege
            }
        }
    }

    override fun signup(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful) {
                callback(true,"RegistrationSuccess",auth.currentUser?.uid.toString())
            }
            else{
                callback(false,it.exception?.message.toString(),"")
            }
        }
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"passsword reset link is sent to $email")
            }
            else{
                callback(false,it.exception?.message.toString())
            }
        }
        }

    override fun addUserToDatabase(
        userID: String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(userModel.userId).setValue(userModel).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true, "Registration Successfully")
            }
            else{
                callback(false,it.exception?.message.toString())
            }
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

}