package com.example.splitit.Fragment

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.splitit.R
import com.example.splitit.home
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class otpverification() : Fragment(), Parcelable {

    private lateinit var pincodeNumber: EditText
    private lateinit var onCodeSentValue: String
    private lateinit var resendCode: TextView
    private lateinit var phoneNumber: EditText

    constructor(parcel: Parcel) : this() {

    }

    constructor(pincodeEnterEditText: EditText,onCodeSentValue:String,resendCode:TextView,phoneNumber:EditText) : this() {
        this.pincodeNumber = pincodeEnterEditText
        this.onCodeSentValue = onCodeSentValue
        this.resendCode = resendCode
        this.phoneNumber = phoneNumber
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otpverification, container, false)
        val proceedOTPbtn = view.findViewById<Button>(R.id.proceedOTPbtn)
        proceedOTPbtn.setOnClickListener {
            val pinCodeLength = pincodeNumber.text.toString().trim()
            if (!pinCodeLength.isEmpty()) {
                if (pinCodeLength.length == 6){
                    if (onCodeSentValue!=null){
                        val phoneAuthCredential = PhoneAuthProvider.getCredential(onCodeSentValue,pinCodeLength)
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener{task ->
                                if (task.isSuccessful){
                                    val intent = Intent(requireContext(),home::class.java)
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                }else{
                                    Toast.makeText(requireContext(), "Enter the correct OTP", Toast.LENGTH_SHORT).show()

                                }
                            }
                    }else{
                        Toast.makeText(requireContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(requireContext(), "OTP verified", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Please Enter all numbers", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(requireContext(), "OTP cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        resendCode.setOnClickListener{
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber.text.toString(),60, TimeUnit.SECONDS,requireActivity(),
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                    }

                    override fun onCodeSent(
                        p0: String,
                        p1: PhoneAuthProvider.ForceResendingToken
                    ) {
                        onCodeSentValue = p0
                        Toast.makeText(requireContext(), "OTP send successfully", Toast.LENGTH_SHORT).show()
                    }

                }

            )
        }
        return view
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<otpverification> {
        override fun createFromParcel(parcel: Parcel): otpverification {
            return otpverification(parcel)
        }

        override fun newArray(size: Int): Array<otpverification?> {
            return arrayOfNulls(size)
        }
    }
}