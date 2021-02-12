package com.uc3m.laboratorio1.views

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uc3m.laboratorio1.R
import com.uc3m.laboratorio1.databinding.FragmentListBinding
import com.uc3m.laboratorio1.databinding.FragmentSaveBinding
import com.uc3m.laboratorio1.models.Student
import com.uc3m.laboratorio1.viewModels.StudentViewModel


class SaveFragment : Fragment() {

    private lateinit var binding: FragmentSaveBinding
    private lateinit var studentViewModel: StudentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSaveBinding.inflate(inflater, container, false)

        studentViewModel = ViewModelProvider(this).get(StudentViewModel::class.java)

        binding.button.setOnClickListener{
            insertDataToDatabase()
        }
        return binding.root
    }

    private fun insertDataToDatabase() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.LastName.text.toString()
        val age = binding.age.text

        if(inputCheck(firstName, lastName, age)){
            val student = Student(0, firstName, lastName, Integer.parseInt(age.toString()))
            studentViewModel.addStudent(student)
            Toast.makeText(requireContext(), "Student created", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_saveFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Fill all the fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || age.isEmpty())
    }
}