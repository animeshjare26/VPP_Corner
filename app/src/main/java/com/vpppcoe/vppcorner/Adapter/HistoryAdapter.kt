package com.vpppcoe.vppcorner.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vpppcoe.vppcorner.Model.History
import com.vpppcoe.vppcorner.R

class HistoryAdapter(private var historyList: ArrayList<History>) : RecyclerView.Adapter<HistoryAdapter.MyViewHolderForHistory>(){

    class MyViewHolderForHistory(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var order_number: TextView = itemView.findViewById(R.id.order_history_number)
        var list: TextView = itemView.findViewById(R.id.items_list_history)
        var price: TextView = itemView.findViewById(R.id.total_price_history)
        var avail: LinearLayout = itemView.findViewById(R.id.linear_layout_for_availability_of_history)
//        var goToCart : TextView = itemView.findViewById(R.id.go_to_cart)
//        var totalAmount: Button = itemView.findViewById(R.id.total_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderForHistory {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.history_item,
            parent, false
        )
        return MyViewHolderForHistory(itemView)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder: MyViewHolderForHistory, position: Int) {
        val currentItem = historyList[position]

        holder.order_number.text = currentItem.order.toString()
        holder.list.text = currentItem.items.toString()
        holder.price.text = currentItem.price.toString()

        if (currentItem.completion.toString() == false.toString()) {
            holder.avail.setBackgroundResource(R.color.not_available)
        } else {
            holder.avail.setBackgroundResource(R.color.available)
        }

    }

    fun updateHistoryList(historyList: List<History>) {
        this.historyList.clear()
        this.historyList.addAll(historyList)
        notifyDataSetChanged()

    }

}