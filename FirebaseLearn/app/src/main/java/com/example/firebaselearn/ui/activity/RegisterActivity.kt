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
import com.example.firebaselearn.ui.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    private var database: FirebaseDatabase=FirebaseDatabase.getInstance()
    private val reference: DatabaseReference=database.reference.child("users") //database.reference = root // child= new dir/table
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()

        binding.loginHREF.setOnClickListener{
            val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.submitButtonReg.setOnClickListener{
            val email :String =binding.editEmailReg.text.toString()
            val password :String=binding.passwordReg.text.toString()
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                if(it.isSuccessful){
                    val userId=auth.currentUser?.uid
                    val userModel= UserModel(  //calling constructor of Model
                        userId.toString(), //userid
                        binding.NameReg.text.toString(), //full name
                        binding.phoneReg.text.toString(), // Phone
                        binding.addrReg.text.toString() //Address
                    )

                    reference.child(userId.toString()).setValue(userModel).addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this@RegisterActivity,"Registration is A success",Toast.LENGTH_SHORT).show()
                            }
                        else{
                            Toast.makeText(this@RegisterActivity,it.exception?.message,Toast.LENGTH_SHORT).show()
                            }
                    }
                }else{
                    Toast.makeText(this@RegisterActivity,it.exception?.message,Toast.LENGTH_SHORT).show()
                    }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}