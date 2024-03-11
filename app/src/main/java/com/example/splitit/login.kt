package com.example.splitit

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.splitit.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class login : AppCompatActivity() {

    private lateinit var emailLogin: EditText
    private lateinit var passwordLogin: EditText
    private lateinit var loginbtn: Button
    private lateinit var userName:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        val binding : ActivityLoginBinding by lazy {
            ActivityLoginBinding.inflate(layoutInflater)
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val splitOnboardText = "\$plitIt"
        val spanableString = SpannableString(splitOnboardText)
        val startIndex = spanableString.indexOf("It")
        val endIndex = startIndex + "It".length
        spanableString.setSpan(StyleSpan(Typeface.BOLD),startIndex,endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val textSplitIt = binding.splititLogin
        textSplitIt.text = spanableString

        emailLogin = binding.editTextTextEmailAddressLogin
        passwordLogin = binding.editTextNumberPasswordLogin
        loginbtn = binding.loginBtn
        userName = binding.editTextUsername

        loginbtn.setOnClickListener{
            if (validEmail()&&validPassword()){
                checkUser()
            }
        }
    }

    private fun checkUser() {
        val userUsername = userName.text.toString().trim()
        val userEmail = emailLogin.text.toString().trim()
        val userPassword = passwordLogin.text.toString().trim()

        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
        val checkUserDatabase: Query = reference.orderByChild("name").equalTo(userUsername)

        checkUserDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userName.error = null
                    val passwordFromDB = snapshot.child(userUsername).child("password").getValue(String::class.java)
                    val confirmpasswordFromDB = snapshot.child(userUsername).child("passwordConfirm").getValue(String::class.java)
                    val emailFromDB = snapshot.child(userUsername).child("email").getValue(String::class.java)

                    if (passwordFromDB == userPassword && emailFromDB == userEmail) {
                        userName.error = null

                        val nameFromDB = snapshot.child(userUsername).child("name").getValue(String::class.java)

                        val intent = Intent(this@login, phoneVerification::class.java)

                        intent.putExtra("name", nameFromDB)
                        intent.putExtra("email", emailFromDB)
                        intent.putExtra("password", passwordFromDB)
                        intent.putExtra("passwordConfirm", confirmpasswordFromDB)

                        startActivity(intent)
                    } else {
                        passwordLogin.error = "Invalid Credentials"
                        passwordLogin.requestFocus()
                    }
                } else {
                    userName.error = "User does not exist"
                    userName.requestFocus()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle cancelled event if needed
            }
        })
    }


    private fun validPassword(): Boolean {
        val valPassword = passwordLogin.text.toString()
        return  if(valPassword.isEmpty()){
            Toast.makeText(this, "Password cannot be Empty", Toast.LENGTH_SHORT).show()
            passwordLogin.error = "Password cannot be empty"
            false
        }else{
            passwordLogin.error=null
            true
        }
    }

    private fun validEmail(): Boolean {
        val valEmail = emailLogin.text.toString()
        return if (valEmail.isEmpty()){
            Toast.makeText(this, "Email cannot be Empty", Toast.LENGTH_SHORT).show()
            emailLogin.error = "Email cannot be Empty"
            false
        }else{
            emailLogin.error = null
            true
        }
    }
}