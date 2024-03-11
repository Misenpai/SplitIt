package com.example.splitit

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.splitit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var  loginbtnOnboard: Button
    private lateinit var  signupbtnOnboard: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val splitOnboardText = "\$plitIt"
        val spanableString = SpannableString(splitOnboardText)
        val startIndex = spanableString.indexOf("It")
        val endIndex = startIndex + "It".length
        spanableString.setSpan(StyleSpan(Typeface.BOLD),startIndex,endIndex,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val textSplitIt = binding.splititOnBoard
        textSplitIt.text = spanableString
        loginbtnOnboard = binding.onboardingLgnButton
        loginbtnOnboard.setOnClickListener{
            val intent = Intent(this@MainActivity,login::class.java)
            startActivity(intent)
        }
        signupbtnOnboard = binding.onboardingSgnBtn
        signupbtnOnboard.setOnClickListener{
            val intent = Intent(this@MainActivity,signup::class.java)
            startActivity(intent)
        }

    }
}