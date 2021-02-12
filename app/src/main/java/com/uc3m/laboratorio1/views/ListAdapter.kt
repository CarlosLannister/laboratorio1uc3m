package com.uc3m.laboratorio1.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uc3m.laboratorio1.databinding.RecyclerViewItemBinding
import com.uc3m.laboratorio1.models.Student

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var studentList = emptyList<Student>()

    class MyViewHolder(val binding: RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent,
                false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = studentList[position]
        with(holder){
            binding.firstName.text = currentItem.firstName.toString()
            binding.LastName.text = currentItem.lastName.toString()
            binding.age.text = currentItem.age.toString()
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    fun setData(studentList: List<Student>){
        this.studentList = studentList
        notifyDataSetChanged()
    }

}