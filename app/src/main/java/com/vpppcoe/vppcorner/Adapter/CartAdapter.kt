package com.vpppcoe.vppcorner.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vpppcoe.vppcorner.Model.Food
import com.vpppcoe.vppcorner.R

class CartAdapter: RecyclerView.Adapter<CartAdapter.MyViewHolder1>() {

    private var tempItems : ArrayList<Food> = temporaryList

    class MyViewHolder1(itemView : View) : RecyclerView.ViewHolder(itemView){

        var name : TextView = itemView.findViewById(R.id.tv_cart_item_name)
        var price : TextView = itemView.findViewById(R.id.tv_cart_item_price)
//        var time : TextView = itemView.findViewById(R.id.tv_time)
//        var avail : LinearLayout = itemView.findViewById(R.id.linear_layout_for_availability)
//        var images : ImageView = itemView.findViewById(R.id.food_images)
//        var add : TextView = itemView.findViewById(R.id.add_to_cart)
//        var remove : TextView = itemView.findViewById(R.id.remove_from_cart)
//        var goToCart : TextView = itemView.findViewById(R.id.go_to_cart)
//        var totalAmount: Button = itemView.findViewById(R.id.total_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cart_item,
            parent,false
        )
        return CartAdapter.MyViewHolder1(itemView)
    }

    override fun getItemCount(): Int {
        return tempItems.size
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {
        val currentItem = tempItems[position]

        holder.name.text = currentItem.name
        holder.price.text = "₹ "+ currentItem.price.toString()
//        holder.time.text = currentItem.time.toString()
//        holder.avail.text = currentItem.available.toString()

//        if (currentItem.available.toString() == false.toString()){
//            holder.avail.setBackgroundResource(R.color.not_available)
//        }else{
//            holder.avail.setBackgroundResource(R.color.back_dark)
//        }

//        holder.add.setOnClickListener {
//            totalAmount += currentItem.price.toString().toInt()
//            val tempItem = currentItem.copy()
//            temporaryList.add(tempItem)
//            Toast.makeText(holder.itemView.context,"Added to cart $totalAmount", Toast.LENGTH_LONG).show()
//        }
//
//        holder.remove.setOnClickListener {
//            totalAmount -= currentItem.price.toString().toInt()
//            val tempItem = currentItem.copy()
//            temporaryList.remove(tempItem)
//            Toast.makeText(holder.itemView.context,"Removed from cart $totalAmount", Toast.LENGTH_LONG).show()
//        }

//        storageRefImg.downloadUrl.addOnSuccessListener {
//            if (it != null) {
//        Glide.with(holder.itemView)
//            .load(currentItem.image.toString())
//            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//            .error(R.drawable.download)
//            .into(holder.images)
//            } else {
//                holder.images.setImageResource(R.drawable.download)
//            }
//        }.addOnFailureListener {
//            holder.images.setImageResource(R.drawable.download)
//        }

    }
}