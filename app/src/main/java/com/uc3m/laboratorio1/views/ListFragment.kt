package com.uc3m.laboratorio1.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.uc3m.laboratorio1.R
import com.uc3m.laboratorio1.databinding.FragmentListBinding
import com.uc3m.laboratorio1.viewModels.StudentViewModel


class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        binding.user.text = currentUser?.displayName

        val adapter = ListAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        studentViewModel = ViewModelProvider(this).get(StudentViewModel::class.java)
        studentViewModel.readAll.observe(viewLifecycleOwner, {
            student -> adapter.setData(student)
        })

        binding.addButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_saveFragment)
        }

        binding.signOutBtn.setOnClickListener{
            auth.signOut()
            findNavController().navigate(R.id.action_listFragment_to_loginFragment)
        }

        return view
    }




}