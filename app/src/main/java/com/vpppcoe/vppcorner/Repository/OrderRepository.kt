package com.vpppcoe.vppcorner.Repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.vpppcoe.vppcorner.Model.Food
import com.vpppcoe.vppcorner.Model.Orders

class OrderRepository {

    private var databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Orders")

    @Volatile private var INSTANCE : OrderRepository? = null

    fun getInstance() : OrderRepository {
        return INSTANCE ?: synchronized(this){
            val instance = OrderRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadOrder(Order : MutableLiveData<List<Orders>>){
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try{

                    val _orderList : List<Orders> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Orders::class.java)!!
                    }

                    Order.postValue(_orderList)
                }catch (e : Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}