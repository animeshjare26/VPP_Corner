package com.vpppcoe.vppcorner.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vpppcoe.vppcorner.Adapter.MyAdapter
import com.vpppcoe.vppcorner.Adapter.ViewOrdersAdapter
import com.vpppcoe.vppcorner.Model.FoodViewModel
import com.vpppcoe.vppcorner.Model.OrderViewModel
import com.vpppcoe.vppcorner.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var viewModel1: OrderViewModel
private lateinit var viewOrdersRecyclerView: RecyclerView
lateinit var adapter3: ViewOrdersAdapter


class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewOrdersRecyclerView = view.findViewById(R.id.view_orders_recycler_view)
//        goToCart = view.findViewById(R.id.go_to_cart)

//        viewOrdersRecyclerView.layoutManager = LinearLayoutManager(context)
//        viewOrdersRecyclerView.setHasFixedSize(true)
//        adapter3 = ViewOrdersAdapter()

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

        viewOrdersRecyclerView.adapter = adapter3
        viewModel1 = ViewModelProvider(this)[OrderViewModel::class.java]
        viewModel1.allOrder.observe(viewLifecycleOwner, Observer {
            adapter3.updateOrderList(it)
        })
    }

}