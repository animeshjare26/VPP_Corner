package com.vpppcoe.vppcorner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vpppcoe.vppcorner.Adapter.MyAdapter
import com.vpppcoe.vppcorner.Adapter.totalAmount
import com.vpppcoe.vppcorner.Model.FoodViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var viewModel: FoodViewModel
private lateinit var foodRecyclerView: RecyclerView
lateinit var adapter: MyAdapter

class HomeFragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var goToCart: TextView

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
        return inflater.inflate(R.layout.fragment_home2, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodRecyclerView = view.findViewById(R.id.food_recycler_view_2)
//        goToCart = view.findViewById(R.id.go_to_cart)

        foodRecyclerView.layoutManager = LinearLayoutManager(context)
        foodRecyclerView.setHasFixedSize(true)
        adapter = MyAdapter()

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

        foodRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this)[FoodViewModel::class.java]
        viewModel.allFood.observe(viewLifecycleOwner, Observer {
            adapter.updateFoodList(it)
        })
    }

//    private fun filterList(newText: String?) {
//        if (newText != null) {
//            val filteredList = ArrayList<Food>()
//            for (i in mList) {
//                if (i.name?.lowercase(Locale.ROOT)?.contains(newText) == true) {
//                    filteredList.add(i)
//                }
//            }
//            if (filteredList.isEmpty()) {
//                Toast.makeText(this.requireContext(), "No item found", Toast.LENGTH_LONG).show()
//            } else {
//                adapter.setFilterdList(filteredList)
//            }
//        }
//    }

}