package com.vpppcoe.vppcorner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.vpppcoe.vppcorner.Fragments.CartFragment
import com.vpppcoe.vppcorner.Fragments.ProfileFragment
import com.vpppcoe.vppcorner.databinding.ActivityNewMainBinding


class NewMainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment2())
        setUpFragment()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

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

}