package com.vpppcoe.vppcorner.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vpppcoe.vppcorner.Repository.FoodRepository
import com.vpppcoe.vppcorner.Repository.HistoryRepository

class HistoryViewModel : ViewModel() {

    private  val repository : HistoryRepository
    private val _allHistory = MutableLiveData<List<History>>()
    val allHistory : LiveData<List<History>> = _allHistory

    init {
        repository = HistoryRepository().getInstance()
        repository.loadHistory(_allHistory)
    }

}