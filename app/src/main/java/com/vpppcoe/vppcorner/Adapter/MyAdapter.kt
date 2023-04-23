package com.vpppcoe.vppcorner.Adapter

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vpppcoe.vppcorner.Model.Food
import com.vpppcoe.vppcorner.NewMainActivity
import com.vpppcoe.vppcorner.R
import kotlinx.coroutines.joinAll

var totalAmount: Int = 0
var temporaryList = ArrayList<Food>()
var tempListOfOrders : String? = " "

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var foodList = ArrayList<Food>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.food_item,
            parent, false
        )
        return MyViewHolder(itemView)
    }

//    fun setFilterdList(mList: ArrayList<Food>){
//        this.mList = foodList
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = foodList[position]

        holder.name.text = currentItem.name
        holder.price.text = "₹ " + currentItem.price.toString()
        holder.time.text = currentItem.time.toString()
//        holder.avail.text = currentItem.available.toString()

        if (currentItem.available.toString() == false.toString()) {
            holder.avail.setBackgroundResource(R.color.not_available)
        } else {
            holder.avail.setBackgroundResource(R.color.back_dark)
        }

        holder.add.setOnClickListener {
            if (currentItem.available.toString() == false.toString()) {
                Toast.makeText(holder.itemView.context, "Item is unavailable", Toast.LENGTH_LONG)
                    .show()
            } else {
                totalAmount += currentItem.price.toString().toInt()
                val tempItem = currentItem.copy()
                temporaryList.add(tempItem)
                Toast.makeText(
                    holder.itemView.context,
                    "Added to cart $totalAmount",
                    Toast.LENGTH_LONG
                ).show()
                tempListOfOrders = tempListOfOrders+ " , "+tempItem.name
            }
        }

        holder.remove.setOnClickListener {
            if (temporaryList.contains(currentItem)) {
                totalAmount -= currentItem.price.toString().toInt()
                val tempItem = currentItem.copy()
                temporaryList.remove(tempItem)
                Toast.makeText(
                    holder.itemView.context,
                    "Removed from cart $totalAmount",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (totalAmount == 0) {
                    Toast.makeText(holder.itemView.context, "Cart is empty", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        holder.itemView.context,
                        "Item not added in cart",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

//        storageRefImg.downloadUrl.addOnSuccessListener {
//            if (it != null) {
        Glide.with(holder.itemView)
            .load(currentItem.image.toString())
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .error(R.drawable.download)
            .into(holder.images)
//            } else {
//                holder.images.setImageResource(R.drawable.download)
//            }
//        }.addOnFailureListener {
//            holder.images.setImageResource(R.drawable.download)
//        }

    }


    fun updateFoodList(foodList: List<Food>) {
        this.foodList.clear()
        this.foodList.addAll(foodList)
        notifyDataSetChanged()

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.tv_name)
        var price: TextView = itemView.findViewById(R.id.tv_price)
        var time: TextView = itemView.findViewById(R.id.tv_time)
        var avail: LinearLayout = itemView.findViewById(R.id.linear_layout_for_availability)
        var images: ImageView = itemView.findViewById(R.id.food_images)
        var add: TextView = itemView.findViewById(R.id.add_to_cart)
        var remove: TextView = itemView.findViewById(R.id.remove_from_cart)
//        var goToCart : TextView = itemView.findViewById(R.id.go_to_cart)
//        var totalAmount: Button = itemView.findViewById(R.id.total_amount)
    }

}