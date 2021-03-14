package com.uc3m.laboratorio1.views

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.uc3m.laboratorio1.databinding.RecyclerViewItemBinding
import com.uc3m.laboratorio1.models.Student


class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var studentList = emptyList<Student>()

    class MyViewHolder(val mContext: Context, val binding: RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.firstName.setOnClickListener{
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked on item ${position +1}", Toast.LENGTH_SHORT).show()

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
                mContext.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent,
                false)

        return MyViewHolder(parent.context, binding)
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