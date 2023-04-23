package com.vpppcoe.vppcorner.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vpppcoe.vppcorner.Repository.FoodRepository

class FoodViewModel : ViewModel() {

    private  val repository : FoodRepository
    private val _allFood = MutableLiveData<List<Food>>()
    val allFood : LiveData<List<Food>> = _allFood

    init {
        repository = FoodRepository().getInstance()
        repository.loadFood(_allFood)
    }

}