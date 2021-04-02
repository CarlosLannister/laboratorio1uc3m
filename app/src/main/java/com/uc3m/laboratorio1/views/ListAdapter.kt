package com.uc3m.laboratorio1.views

import android.content.Context
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.uc3m.laboratorio1.R
import com.uc3m.laboratorio1.databinding.RecyclerViewItemBinding
import com.uc3m.laboratorio1.models.Student
import com.uc3m.laboratorio1.viewModels.StudentViewModel

class ListAdapter(private val viewModel: StudentViewModel) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var studentList = emptyList<Student>()

    class MyViewHolder(val mContext: Context,
                       val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            var navController: NavController? = null
            binding.firstName.setOnClickListener{
                val position: Int = adapterPosition
                navController = Navigation.findNavController(itemView)

                navController!!.navigate(R.id.action_listFragment_to_saveFragment)
                Toast.makeText(itemView.context, "You clicked on item ${position +1}", Toast.LENGTH_SHORT).show()

                /*
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
                mContext.startActivity(intent)

                 */
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
            //binding.age.text = currentItem.age.toString()
            val iv: ByteArray = Base64.decode(currentItem.iv, Base64.DEFAULT)
            val text: ByteArray = Base64.decode(currentItem.encryptedAge, Base64.DEFAULT)

            binding.age.text = viewModel.decryptData(iv, text)
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
