package com.vpppcoe.vppcorner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.vpppcoe.vppcorner.databinding.ActivityUpdateItemInfoBinding

class UpdateItemInfo : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateItemInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateItemInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        binding.updateMenu.setOnClickListener {
            startActivity(Intent(this,UpdateMenu::class.java))
        }

        binding.viewOrders.setOnClickListener {
            startActivity(Intent(this,ViewOrders::class.java))
        }

    }
}