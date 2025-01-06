package com.example.firebaselearn.repository

import com.example.firebaselearn.ui.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    fun login(email:String,password:String,callback:(Boolean,String)->Unit)
    fun signup(email:String,password:String,callback: (Boolean, String, String) -> Unit)
    fun forgetPassword(email:String,callback: (Boolean, String) -> Unit)
    fun addUserToDatabase(userID:String, userModel: UserModel, callback: (Boolean, String) -> Unit)
    fun getCurrentUser():FirebaseUser?
}
