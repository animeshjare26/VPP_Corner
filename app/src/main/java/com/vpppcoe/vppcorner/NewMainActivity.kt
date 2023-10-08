package com.vpppcoe.vppcorner

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.oAuthCredential
import com.google.firebase.database.*
import com.vpppcoe.vppcorner.Adapter.ViewOrdersAdapter
import com.vpppcoe.vppcorner.Fragments.CartFragment
import com.vpppcoe.vppcorner.Fragments.ProfileFragment
import com.vpppcoe.vppcorner.Model.Orders
import com.vpppcoe.vppcorner.databinding.ActivityNewMainBinding


class NewMainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewMainBinding
    private lateinit var dbRed : DatabaseReference
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment2())
        setUpFragment()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        auth = FirebaseAuth.getInstance()
//        getOrderLists()

    }

    private fun setUpFragment() {
        binding.bottomNav.setItemSelected(R.id.nav_home)
        binding.bottomNav.setOnItemSelectedListener {
            when (it){
                R.id.nav_home -> replaceFragment(HomeFragment2())
                R.id.nav_cart -> replaceFragment(CartFragment())
                R.id.nav_profile -> replaceFragment(ProfileFragment())
            }
        }
    }

    private fun replaceFragment(home: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,home)
        fragmentTransaction.commit()
    }

    fun getOrderLists() {
        dbRed = FirebaseDatabase.getInstance().getReference("History")
        val new = dbRed.child("${auth.currentUser?.email.toString().subSequence(0,11)}")
            .child("orders")

        new.addValueEventListener(object  : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.S)
            override fun onDataChange(snapshot: DataSnapshot) {
                val msg = "Order Completed"
                NotificationHelper(applicationContext,msg).Notification()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}