package com.vpppcoe.vppcorner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vpppcoe.vppcorner.Adapter.MyAdapter
import com.vpppcoe.vppcorner.databinding.ActivityUpdateMenuBinding

class UpdateMenu : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateMenuBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_update_menu)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)



    }
}