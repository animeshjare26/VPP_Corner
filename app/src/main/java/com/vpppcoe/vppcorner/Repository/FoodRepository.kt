package com.vpppcoe.vppcorner.Repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.vpppcoe.vppcorner.Model.Food

class FoodRepository {

    private var databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Food")

    @Volatile private var INSTANCE : FoodRepository? = null

    fun getInstance() : FoodRepository {
        return INSTANCE ?: synchronized(this){
            val instance = FoodRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadFood(foodList : MutableLiveData<List<Food>>){
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try{

                    val _foodList : List<Food> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Food::class.java)!!
                    }

                    foodList.postValue(_foodList)
                }catch (e : Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("dataabase", error.message)
            }

        })
    }

}