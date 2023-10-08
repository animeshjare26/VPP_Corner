package com.vpppcoe.vppcorner.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.vpppcoe.vppcorner.*
import com.vpppcoe.vppcorner.Adapter.HistoryAdapter
import com.vpppcoe.vppcorner.Adapter.ViewOrdersAdapter
import com.vpppcoe.vppcorner.Model.FoodViewModel
import com.vpppcoe.vppcorner.Model.History
import com.vpppcoe.vppcorner.Model.HistoryViewModel
import com.vpppcoe.vppcorner.Model.Orders
import com.vpppcoe.vppcorner.R
import java.io.ByteArrayOutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var auth : FirebaseAuth
private lateinit var db : FirebaseDatabase
private lateinit var viewHistoryRecyclerView : RecyclerView
private lateinit var adapter4: HistoryAdapter
private lateinit var historyList : ArrayList<History>
private lateinit var dbRed : DatabaseReference
private lateinit var viewModelForHistory : HistoryViewModel
private lateinit var storageReference: StorageReference
private var Uri: Uri? = null

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
        auth = FirebaseAuth.getInstance()
//        val signOut : Button = view.findViewById(R.id.sign_out_btn)
        val name : TextView = view.findViewById(R.id.user_name)
        val email : TextView = view.findViewById(R.id.user_email)
        val profilePic : ImageView = view.findViewById(R.id.profile_pic)
        val userEmail = auth.currentUser?.email.toString().subSequence(0,11)
        val click : LinearLayout = view.findViewById(R.id.click_bottom)
        db = FirebaseDatabase.getInstance()
        val dbForUserInfo = db.getReference("User")

        viewHistoryRecyclerView = view.findViewById(R.id.food_history_recycler_view)
//        goToCart = view.findViewById(R.id.go_to_cart)
        historyList = arrayListOf()

        viewHistoryRecyclerView.layoutManager = LinearLayoutManager(context)
        viewHistoryRecyclerView.setHasFixedSize(true)
        getHistoryList()
//        adapter4 = HistoryAdapter(historyList)

//        viewHistoryRecyclerView.adapter = adapter4
//        viewModelForHistory = ViewModelProvider(this)[HistoryViewModel::class.java]
//        viewModelForHistory.allHistory.observe(viewLifecycleOwner, Observer {
//            adapter4.updateHistoryList(it)
//        })

        dbForUserInfo.child(userEmail.toString()).get().addOnSuccessListener {
            if(it.exists()){
                name.text = it.child("name").value.toString()
                Glide.with(this.requireContext())
                    .load(it.child("image").value.toString())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.profile_icon)
                    .into(profilePic)
            }else{
                Toast.makeText(this.requireContext(),"Not Found",Toast.LENGTH_SHORT).show()
            }
        }

//        val image = db.getReference("User")
//        val url = image.child(auth.currentUser?.email.toString().subSequence(0,11).toString())
//            .child("image")
//        url.get().addOnCompleteListener {
//        }

        email.text = auth.currentUser?.email.toString()

        click.setOnClickListener {
            BottomLogoutSheet().show(parentFragmentManager,"YOYOYOY")
        }

//        signOut.setOnClickListener {
//            val builder = AlertDialog.Builder(this.context)
//            //set title for alert dialog
//            builder.setTitle("Logout")
//            //set message for alert dialog
//            builder.setMessage("Do you really want to logout?")
//            builder.setIcon(R.drawable.baseline_error_24)
//
//            //performing positive action
//            builder.setPositiveButton(Html.fromHtml("<font color='#31DF0707'>Yes</font>")){ dialogInterface, which ->
//                auth.signOut()
//                activity?.finish()
//                startActivity(Intent(this.context, LoginActivity::class.java))
//            }
//            //performing negative action
//            builder.setNegativeButton(Html.fromHtml("<font color='#0E121A'>No</font>")) {dialogInterface, which ->
//                return@setNegativeButton
//            }
//            // Create the AlertDialog
//            val alertDialog: AlertDialog = builder.create()
//            // Set other dialog properties
//            alertDialog.setCancelable(false)
//            alertDialog.show()
//        }


    }

    private fun getHistoryList() {
            dbRed = FirebaseDatabase.getInstance().getReference("History/${auth.currentUser?.email.toString().subSequence(0,11)}/orders")

            dbRed.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    historyList.clear()
                    if(snapshot.exists()){
                        for(history in snapshot.children){
                            val historyData = history.getValue((History::class.java))
                            historyList.add(historyData!!)
                        }
                        adapter4 = HistoryAdapter(historyList)
                        viewHistoryRecyclerView.adapter = adapter4
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
}