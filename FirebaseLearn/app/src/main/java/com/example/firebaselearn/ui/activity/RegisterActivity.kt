package com.example.firebaselearn.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaselearn.R
import com.example.firebaselearn.databinding.ActivityRegisterBinding
import com.example.firebaselearn.repository.UserRepositoryImpl
import com.example.firebaselearn.ui.UserModel
import com.example.firebaselearn.viewmidel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userRepository = UserRepositoryImpl()
        userViewModel=UserViewModel(userRepository)

        binding.loginHREF.setOnClickListener{
            val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.submitButtonReg.setOnClickListener{
            val email :String =binding.editEmailReg.text.toString()
            val password :String=binding.passwordReg.text.toString()
            userViewModel.signup(email,password){
                success,messege,userid ->
                if (success){
                    val userModel=UserModel(
                        userid,
                        binding.NameReg.toString(),
                        binding.phoneReg.toString(),
                        binding.addrReg.toString(),
                    )
                    addUser(userModel)
                }else{
                    Toast.makeText(this@RegisterActivity,messege,Toast.LENGTH_SHORT).show()

                }


            }

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addUser(userModel: UserModel) {
        userViewModel.addUserToDatabase(userModel.userId, userModel) { success, message ->
            if (success) {
                Toast.makeText(
                    this@RegisterActivity, message, Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@RegisterActivity, message, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}