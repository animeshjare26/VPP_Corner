package com.vpppcoe.vppcorner.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.vpppcoe.vppcorner.LoginActivity
import com.vpppcoe.vppcorner.R
import com.vpppcoe.vppcorner.databinding.FragmentBottomLogoutSheetBinding

class BottomLogoutSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomLogoutSheetBinding
    private lateinit var auth : FirebaseAuth
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomLogoutSheetBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.userEmailBottom.text = auth.currentUser?.email.toString()
        binding.buttonLogoutBottom.setOnClickListener {
            auth.signOut()
            activity?.finish()
            startActivity(Intent(this.context,LoginActivity::class.java))
        }

    }
}