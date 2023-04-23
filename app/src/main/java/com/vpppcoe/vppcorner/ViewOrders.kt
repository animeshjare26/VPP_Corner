package com.vpppcoe.vppcorner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vpppcoe.vppcorner.Adapter.ViewOrdersAdapter
import com.vpppcoe.vppcorner.Fragments.adapter2
import com.vpppcoe.vppcorner.Model.FoodViewModel
import com.vpppcoe.vppcorner.Model.OrderViewModel
import com.vpppcoe.vppcorner.databinding.ActivityViewOrdersBinding

class ViewOrders : AppCompatActivity() {

    private lateinit var viewModel: OrderViewModel
    private lateinit var viewOrdersRecyclerView: RecyclerView
    private lateinit var adapter3: ViewOrdersAdapter

    private lateinit var binding : ActivityViewOrdersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        viewOrdersRecyclerView = binding.viewOrdersRecyclerView

//        goToCart = view.findViewById(R.id.go_to_cart)

        viewOrdersRecyclerView.layoutManager = LinearLayoutManager(this)
        viewOrdersRecyclerView.setHasFixedSize(true)
        adapter3 = ViewOrdersAdapter()
        viewOrdersRecyclerView.adapter = adapter3

        viewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        viewModel.allOrder.observe(this, Observer {
            adapter3.updateOrderList(it)
        })
    }
}