package com.vpppcoe.vppcorner

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.vpppcoe.vppcorner.Adapter.tempListOfOrders
import com.vpppcoe.vppcorner.Adapter.temporaryList
import com.vpppcoe.vppcorner.Adapter.totalAmount
import com.vpppcoe.vppcorner.Fragments.additinalInfoString
import com.vpppcoe.vppcorner.Model.Orders
import com.vpppcoe.vppcorner.databinding.ActivityCheckOutBinding
import kotlinx.coroutines.joinAll
import org.json.JSONObject
import java.time.LocalTime

var counterVar : Int = 0

class CheckOut : AppCompatActivity(), PaymentResultListener {

//    private var totalAmount = totalAmount()
    private var new = totalAmount
    private var amount : Int = new
    private lateinit var database : DatabaseReference
    private lateinit var databaseForOrderNumber : DatabaseReference
    private var listOfOrders : String? = tempListOfOrders.toString()
    private lateinit var binding: ActivityCheckOutBinding
//    private var totalTv :TextView = findViewById(R.id.tv_total_amount_check_out)
//    private var paymentTv :Button = findViewById(R.id.btn_pay)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_check_out)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
//        val intent = intent
//        val totalAmount = intent.getIntExtra("totalAmount",0)
//        Toast.makeText(this,"Total amount $totalAmount",Toast.LENGTH_LONG).show()

//        binding.tvTotalAmountCheckOut.text = totalAmount.toString()
        binding.tvTotalAmountCheckOut.text = new.toString()
        binding.btnPay.text = "Proceed to pay ₹ "+new.toString()
        binding.btnPay.setOnClickListener {
            makePayment()
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun makePayment() {
        val co = Checkout()
        getOrderNumber()
        try {
            val options = JSONObject()
            options.put("name","Vishal Ugalmugale")
//            options.put("description","Payment of food")
            //You can omit the image option to fetch the image from the dashboard
//            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            options.put("amount","${amount*100}")//pass amount in currency subunits

            val prefill = JSONObject()
            prefill.put("email","")
            prefill.put("contact","8425990154")

            options.put("prefill",prefill)
            co.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPaymentSuccess(p0: String?) {
        val paymentSuccess = true
        val time = LocalTime.now().toString()
        val addition = additinalInfoString.toString()
        val Counter = counterVar
        val Amount = new
        var orders = listOfOrders
        databaseForOrderNumber = FirebaseDatabase.getInstance().getReference("number")
        databaseForOrderNumber.setValue(counterVar+1)
        temporaryList.clear()
            updateData(addition,amount, orders!!,Counter,time,paymentSuccess)
        startActivity(Intent(this,NewMainActivity::class.java))
        Toast.makeText(this,"Payment Successful $p0",Toast.LENGTH_LONG).show()
    }

    private fun updateData(
        addition: String,
        amount: Int,
        items:String,
        counter : Int,
        time: String,
        paymentSuccess: Boolean
    ) {
        database = FirebaseDatabase.getInstance().getReference("Orders")
        val orders = Orders(addition,amount, items, counter,time,paymentSuccess)

        totalAmount = 0
        database = FirebaseDatabase.getInstance().getReference("Orders")
        database.child(counter.toString()).setValue(orders)
            .addOnCompleteListener {
                Toast.makeText(this,"Order Placed Successfully",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {execption ->
                Log.e("Vishal", execption.toString())
            }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Payment Unsuccessful $p1",Toast.LENGTH_LONG).show()
    }

    private fun getOrderNumber(){
        databaseForOrderNumber = FirebaseDatabase.getInstance().getReference("number")
        databaseForOrderNumber.get().addOnSuccessListener {
            if(it.exists()){
                counterVar = it.value.toString().toInt()
//                Toast.makeText(this, counterVar.toString(),Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,databaseForOrderNumber.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }
}