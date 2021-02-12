package com.uc3m.laboratorio1.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.uc3m.laboratorio1.models.Student
import com.uc3m.laboratorio1.models.StudentDatabase
import com.uc3m.laboratorio1.models.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StudentViewModel(application: Application): AndroidViewModel(application) {

    val readAll: LiveData<List<Student>>
    private val repository: StudentRepository

    init {
        val studentDao = StudentDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(studentDao)
        readAll = repository.readAll
    }

    fun addStudent(student: Student){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStudent(student)
        }
    }

}