package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var name:EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        name=findViewById(R.id.name)
        edtEmail=findViewById(R.id.email)
        edtPassword=findViewById(R.id.password)
        btnSignUp=findViewById(R.id.button_signup)

        btnSignUp.setOnClickListener {
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            val nm=name.text.toString()

            signUp(nm,email,password)
        }


    }

    private fun signUp(nm : String,email: String, password: String) {
        //logic of creating user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Code for logging in user
                    addUsertoDatabase(nm,email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                }  else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Error! Please try again",Toast.LENGTH_SHORT).show()

                    }

                }
            }

    private fun addUsertoDatabase(nm: String, email: String, uid: String?) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        if (uid != null) {
            mDbRef.child("user").child(uid).setValue(User(nm,email, uid))
        }




    }


}
