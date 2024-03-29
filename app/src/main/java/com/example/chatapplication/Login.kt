package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnSignUp:Button

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()


        mAuth = FirebaseAuth.getInstance()
        edtEmail=findViewById(R.id.email)
        edtPassword=findViewById(R.id.password)
        btnLogin=findViewById(R.id.button_login)
        btnSignUp=findViewById(R.id.button_signup)

        btnSignUp.setOnClickListener {
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email=edtEmail.text.toString();
            val password=edtPassword.text.toString();

            login(email,password)

        }
    }

    private fun login(email: String, password: String) {
        //logic for logging the user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Code for logging in user
                    finish()
                    val intent = Intent(this@Login,MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login,"Invalid credential! Please try again",Toast.LENGTH_LONG).show()

                }
            }



    }
}