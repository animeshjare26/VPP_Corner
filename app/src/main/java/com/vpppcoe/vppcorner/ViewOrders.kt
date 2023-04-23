package com.vpppcoe.vppcorner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.vpppcoe.vppcorner.Adapter.ViewOrdersAdapter
import com.vpppcoe.vppcorner.Fragments.adapter2
import com.vpppcoe.vppcorner.Model.FoodViewModel
import com.vpppcoe.vppcorner.Model.OrderViewModel
import com.vpppcoe.vppcorner.Model.Orders
import com.vpppcoe.vppcorner.databinding.ActivityViewOrdersBinding
import kotlinx.coroutines.joinAll

class ViewOrders : AppCompatActivity() {

//    private lateinit var viewModel: OrderViewModel
    private lateinit var viewOrdersRecyclerView : RecyclerView
    private lateinit var adapter3: ViewOrdersAdapter
    private lateinit var orderList : ArrayList<Orders>
    private lateinit var dbRed : DatabaseReference

    private lateinit var binding : ActivityViewOrdersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        viewOrdersRecyclerView = binding.viewOrdersRecyclerView
        orderList = arrayListOf()


//        goToCart = view.findViewById(R.id.go_to_cart)

        viewOrdersRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        viewOrdersRecyclerView.setHasFixedSize(true)
        getOrderLists()
//        adapter3 = ViewOrdersAdapter(viewModel)
//        viewOrdersRecyclerView.adapter = adapter3

//        viewModel = ViewModelProvider(this)[OrderViewModel::class.java]
//        viewModel.allOrder.observe(this, Observer {
//            adapter3.updateOrderList(it)
//        })
    }

    private fun getOrderLists() {
        dbRed = FirebaseDatabase.getInstance().getReference("Orders")

        dbRed.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList.clear()
                if(snapshot.exists()){
                    for(orders in snapshot.children){
                        val ordersData = orders.getValue((Orders::class.java))
                        orderList.add(ordersData!!)
                    }
                    adapter3 = ViewOrdersAdapter(orderList)
                    viewOrdersRecyclerView.adapter = adapter3
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}