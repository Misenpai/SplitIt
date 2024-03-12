package com.example.splitit

import android.os.Bundle
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.splitit.Fragment.otpverification
import com.example.splitit.databinding.ActivityPhoneVerificationBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class phoneVerification : AppCompatActivity() {

    private lateinit var fragmentContainer: FrameLayout
    private lateinit var pinFrameLayout: FrameLayout
    private lateinit var pinText: TextView
    private lateinit var resendText: TextView
    private lateinit var pincodeEnter : EditText
    private lateinit var phoneNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding : ActivityPhoneVerificationBinding by lazy{
            ActivityPhoneVerificationBinding.inflate(layoutInflater)
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.main)

        fragmentContainer = binding.sendOTPFrame
        pinFrameLayout = binding.pinFrame
        pinText = binding.textView9
        resendText = binding.textView10
        pincodeEnter = binding.editTextNumber2
        phoneNumber = binding.editTextPhone3
        binding.sendOTPbtn.setOnClickListener{

            if (!phoneNumber.text.toString().trim().isEmpty()){
                if ((phoneNumber.text.toString().trim()).length == 10){


                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + phoneNumber.text.toString(),60, TimeUnit.SECONDS,this,
                        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                                Toast.makeText(this@phoneVerification, "Phone Number Verified", Toast.LENGTH_SHORT).show()
                            }

                            override fun onVerificationFailed(p0: FirebaseException) {
                                Toast.makeText(this@phoneVerification, p0.message, Toast.LENGTH_SHORT).show()
                            }

                            override fun onCodeSent(
                                p0: String,
                                p1: PhoneAuthProvider.ForceResendingToken
                            ) {
                                showOTPVerificationFragment(p0)
                                pinFrameLayout.isEnabled = true
                                pinFrameLayout.findViewById<TextView>(R.id.textView9).isEnabled = true
                                pinFrameLayout.findViewById<TextView>(R.id.textView10).isEnabled = true
                                pinFrameLayout.findViewById<EditText>(R.id.editTextNumber2).isEnabled = true
                                pinFrameLayout.setBackgroundResource(R.drawable.phone_no_background)
                                pinText.setTextColor(ContextCompat.getColor(this@phoneVerification,R.color.white))
                                resendText.setTextColor(ContextCompat.getColor(this@phoneVerification,R.color.white))
                                pincodeEnter.setTextColor(ContextCompat.getColor(this@phoneVerification,R.color.white))
                            }

                        }

                    )

                }else{
                    Toast.makeText(this, "Please enter correct number", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun showOTPVerificationFragment(onCodeSentValue:String) {
        val otpVerificationFragment = otpverification(pincodeEnter,onCodeSentValue,resendText,phoneNumber)
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer.id,otpVerificationFragment)
            .commit()

    }
}