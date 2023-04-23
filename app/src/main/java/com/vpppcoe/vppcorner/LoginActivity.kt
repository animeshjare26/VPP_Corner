package com.vpppcoe.vppcorner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.vpppcoe.vppcorner.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var admin : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        // Firebase Instance
        auth = FirebaseAuth.getInstance()
        binding.loadingAnimation.pauseAnimation()
        binding.loginBtn.visibility = View.VISIBLE

        binding.adminLogin.setOnClickListener {
            startActivity(Intent(this,AdminLoginPage::class.java))
            finish()
        }

        binding.email.setOnClickListener {
            binding.tvWrongEmail.visibility = View.GONE
            binding.tvEmptyField.visibility = View.GONE
        }
        binding.password.setOnClickListener {
            binding.tvWrongEmail.visibility = View.GONE
            binding.tvEmptyField.visibility = View.GONE
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            binding.tvWrongEmail.visibility = View.GONE
            binding.tvEmptyField.visibility = View.GONE
            binding.loadingAnimation.visibility = View.VISIBLE
            binding.loginBtn.visibility = View.GONE
            binding.loadingAnimation.playAnimation()
            // Email/Password Login Logic

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, NewMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        binding.loadingAnimation.visibility = View.GONE
                        binding.loadingAnimation.pauseAnimation()
                        binding.tvWrongEmail.visibility = View.VISIBLE
                        binding.loginBtn.visibility = View.VISIBLE
                        binding.email.text.clear()
                        binding.password.text.clear()
                    }
                }
            } else {
                binding.loadingAnimation.visibility = View.GONE
                binding.loadingAnimation.pauseAnimation()
                binding.tvEmptyField.visibility = View.VISIBLE
                binding.loginBtn.visibility = View.VISIBLE
            }
        }

        binding.btnForgotPass.setOnClickListener {
            startActivity(Intent(this, Forgot_Pass::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null && auth.currentUser?.email != "vishalaua23@gmail.com"){
            val intent = Intent(this, NewMainActivity::class.java)
            intent.putExtra("Email",binding.email.text.toString())
            startActivity(intent)
            finish()
        }
    }
}