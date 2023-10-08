package com.vpppcoe.vppcorner.Repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.vpppcoe.vppcorner.Model.Food
import com.vpppcoe.vppcorner.Model.History

class HistoryRepository {

    private var databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("History")

    @Volatile private var INSTANCE : HistoryRepository? = null

    fun getInstance() : HistoryRepository {
        return INSTANCE ?: synchronized(this){
            val instance = HistoryRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadHistory(historyList : MutableLiveData<List<History>>){
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try{

                    val _historyList : List<History> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(History::class.java)!!
                    }

                    historyList.postValue(_historyList)
                }catch (e : Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}