package com.example.splitit

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.splitit.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {

    private lateinit var fullName: EditText
    private lateinit var signupemail: EditText
    private lateinit var signuppassword: EditText
    private lateinit var signuppasswordconfrm: EditText
    private lateinit var signupbtn: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding: ActivitySignupBinding by lazy {
            ActivitySignupBinding.inflate(layoutInflater)
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val splitOnboardText = "\$plitIt"
        val spanableString = SpannableString(splitOnboardText)
        val startIndex = spanableString.indexOf("It")
        val endIndex = startIndex + "It".length
        spanableString.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val textSplitIt = binding.splititSignup
        textSplitIt.text = spanableString

        auth = FirebaseAuth.getInstance()

        fullName = binding.editTextTextSignup
        signupemail = binding.editTextTextEmailAddressSignup
        signuppassword = binding.editTextNumberPasswordSignUp
        signuppasswordconfrm = binding.editTextNumberConfirmPasswordSignUp
        signupbtn = binding.signupBtn

        signupbtn.setOnClickListener {
            database = FirebaseDatabase.getInstance()
            reference = database.getReference("users")

            val name = fullName.text.toString()
            val emailSignup = signupemail.text.toString()
            val passwordSignup = signuppassword.text.toString()
            val passwordConfirmSignup = signuppasswordconfrm.text.toString()

            if (name.isEmpty() || emailSignup.isEmpty() || passwordSignup.isEmpty() || passwordConfirmSignup.isEmpty()) {
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else if (passwordSignup != passwordConfirmSignup) {
                Toast.makeText(this, "Repeat Password must be same", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(emailSignup, passwordSignup)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Successfully added to authentication",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, login::class.java)
                            startActivity(intent)
                        } else {
                            Log.e("FirebaseAuth", "Registration failed", task.exception)
                            Toast.makeText(
                                this,
                                "Registraion Failed : ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                val helperClass = HelperClass(name,emailSignup,passwordSignup,passwordConfirmSignup)
                reference.child(name).setValue(helperClass)


                Toast.makeText(this, "You have signed up successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,login::class.java)
                startActivity(intent)
            }


        }

    }
}