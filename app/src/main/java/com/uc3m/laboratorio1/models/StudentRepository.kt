package com.uc3m.laboratorio1.models

import androidx.lifecycle.LiveData

class StudentRepository(private val studentDao: StudentDao) {

    val readAll: LiveData<List<Student>> = studentDao.readAll()

    suspend fun addStudent(student: Student){
        studentDao.addStudent(student)
    }
}