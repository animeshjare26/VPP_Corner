package com.vpppcoe.vppcorner.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vpppcoe.vppcorner.Repository.OrderRepository

class OrderViewModel : ViewModel() {

    private  val repository1 : OrderRepository
    private val _allOrders = MutableLiveData<List<Orders>>()
    val allOrder : LiveData<List<Orders>> = _allOrders

    init {
        repository1 = OrderRepository().getInstance()
        repository1.loadOrder(_allOrders)
    }

}