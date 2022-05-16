package com.rishabhkumar.whatsapp_clone.Fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.rishabhkumar.whatsapp_clone.R

class SignUp : Fragment() {
    private lateinit var enterEmail: TextInputEditText
    private lateinit var enterPassword: TextInputEditText
    private lateinit var confirmPassword: TextInputEditText
    private lateinit var signUpButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var fauth : FirebaseAuth
    private lateinit var fstore : FirebaseFirestore
    private lateinit var db : DocumentReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        enterEmail = view.findViewById(R.id.edtSignUPEmail)
        enterPassword = view.findViewById(R.id.edtSignUPPassword)
        confirmPassword = view.findViewById(R.id.edtSignUPConfirmPassword)
        signUpButton = view.findViewById(R.id.btnSignUP)
        progressBar = view.findViewById(R.id.signUPProgressBar)

        fauth = FirebaseAuth.getInstance()
        fstore = FirebaseFirestore.getInstance()
        signUpButton.setOnClickListener{
            val email = enterEmail.text.toString()
            val password = enterPassword.text.toString()
            val confirmPasswordd = confirmPassword.text.toString()
            if(TextUtils.isEmpty(email)){
                enterEmail.error = "Email is required."
            }else if(TextUtils.isEmpty(password)){
                enterPassword.error = "Password is required."
            }else if(TextUtils.isEmpty(confirmPasswordd)){
                confirmPassword.error = "Please re-enter password"
            }else if(TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPasswordd)){
                enterPassword.error = "Password is required."
                confirmPassword.error = "Please re-enter password"
            }else if(password.length < 8){
                enterPassword.error = "Password must be greater than 8 letters"
            }else if(password != confirmPasswordd){
                confirmPassword.error = "Password must be same"
            }else{
                progressBar.visibility = View.VISIBLE
                createAccount(email,password)
            }
        }

        return view
    }


    private fun createAccount(email: String, password: String) {
        fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            task->
            if(task.isSuccessful){
                val userInfo = fauth.currentUser?.uid
                db = fstore.collection("users").document(userInfo.toString())
                val obj = mutableMapOf<String,String>()
                obj["userEmail"] = email
                obj["userPassword"] = password
                db.set(obj).addOnSuccessListener {
                    Log.d("onSuccess","User Created Successfully")
                    progressBar.visibility = View.GONE
                }
            }
        }
    }
}