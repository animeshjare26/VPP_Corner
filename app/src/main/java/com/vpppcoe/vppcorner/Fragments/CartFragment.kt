package com.vpppcoe.vppcorner.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vpppcoe.vppcorner.Adapter.CartAdapter
import com.vpppcoe.vppcorner.Adapter.MyAdapter
import com.vpppcoe.vppcorner.Adapter.temporaryList
import com.vpppcoe.vppcorner.Adapter.totalAmount
import com.vpppcoe.vppcorner.CheckOut
import com.vpppcoe.vppcorner.Model.FoodViewModel
import com.vpppcoe.vppcorner.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var cartRecyclerView: RecyclerView
lateinit var adapter2: CartAdapter
var additinalInfoString : String? = null

class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var proceedToPay: TextView
    private lateinit var addtionalInfo : EditText
    private lateinit var totalValue : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartRecyclerView = view.findViewById(R.id.cart_food_recycler_view)
        proceedToPay = view.findViewById(R.id.proceed_to_pay)
        addtionalInfo = view.findViewById(R.id.additional_info)
        totalValue = view.findViewById(R.id.total_amount)


//        goToCart = view.findViewById(R.id.go_to_cart)

//        goToCart.setOnClickListener {
//            val intent = Intent(this.requireContext(), CheckOut::class.java)
//            startActivity(intent)
//        }

        totalValue.text = "Total = " + totalAmount.toString()

        cartRecyclerView.layoutManager = LinearLayoutManager(context)
        cartRecyclerView.setHasFixedSize(true)
        adapter2 = CartAdapter()

        proceedToPay.setOnClickListener {
            if(addtionalInfo.text.isNotEmpty() && temporaryList.isNotEmpty()){
                storeAdditionalInfo(addtionalInfo.text.toString())
                startActivity(Intent(this.requireContext(),CheckOut::class.java))
            }else{
                Toast.makeText(this.requireContext(),"Empty Cart/Additional info",Toast.LENGTH_SHORT).show()
            }
        }

//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                filterList(newText)
//
//
//                return true
//            }
//
//        })

        cartRecyclerView.adapter = adapter2
//        viewModel = ViewModelProvider(this)[FoodViewModel::class.java]
//        viewModel.allFood.observe(viewLifecycleOwner, Observer {
//            com.vpppcoe.vppcorner.adapter2.updateFoodList(it)
//        })
    }

    private fun storeAdditionalInfo(info : String) {
        additinalInfoString = info
    }

}