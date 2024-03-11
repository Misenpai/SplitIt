package com.example.splitit

import android.os.Bundle
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.splitit.Fragment.otpverification
import com.example.splitit.databinding.ActivityPhoneVerificationBinding

class phoneVerification : AppCompatActivity() {

    private lateinit var fragmentContainer: FrameLayout
    private lateinit var pinFrameLayout: FrameLayout
    private lateinit var pinText: TextView
    private lateinit var resendText: TextView
    private lateinit var pincodeEnter : EditText

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
        binding.sendOTPbtn.setOnClickListener{
            showOTPVerificationFragment()
            pinFrameLayout.isEnabled = true
            pinFrameLayout.findViewById<TextView>(R.id.textView9).isEnabled = true
            pinFrameLayout.findViewById<TextView>(R.id.textView10).isEnabled = true
            pinFrameLayout.findViewById<EditText>(R.id.editTextNumber2).isEnabled = true
            pinFrameLayout.setBackgroundResource(R.drawable.phone_no_background)
            pinText.setTextColor(ContextCompat.getColor(this,R.color.white))
            resendText.setTextColor(ContextCompat.getColor(this,R.color.white))
            pincodeEnter.setTextColor(ContextCompat.getColor(this,R.color.white))
        }
    }

    private fun showOTPVerificationFragment() {
        val otpVerificationFragment = otpverification()
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer.id,otpVerificationFragment)
            .commit()

    }
}