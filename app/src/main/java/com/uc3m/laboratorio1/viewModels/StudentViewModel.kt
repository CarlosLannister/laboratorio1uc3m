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
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec


class StudentViewModel(application: Application): AndroidViewModel(application) {

    val readAll: LiveData<List<Student>>
    private val repository: StudentRepository

    init {
        val studentDao = StudentDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(studentDao)
        readAll = repository.readAll
    }

    fun encryptData(data: String): Pair<ByteArray, ByteArray> {
        val cipher: Cipher = Cipher.getInstance("AES/CBC/NoPadding")

        var temp: String = data
        while (temp.toByteArray().size % 16 != 0)
            temp += "\u0020"

        cipher.init(Cipher.ENCRYPT_MODE, getKey())

        val ivBytes = cipher.iv

        val encryptedBytes = cipher.doFinal(temp.toByteArray())

        return Pair(ivBytes, encryptedBytes)
    }

    fun addStudent(student: Student){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStudent(student)
        }
    }
    fun checkKey(): Boolean {
        val keystore: KeyStore = KeyStore.getInstance("AndroidKeyStore")
        keystore.load(null)
        val secretKeyEntry = keystore.getEntry("MyKeyStore", null) as KeyStore.SecretKeyEntry
        return secretKeyEntry.secretKey != null
    }
    fun getKey(): SecretKey {
        val keystore: KeyStore = KeyStore.getInstance("AndroidKeyStore")
        keystore.load(null)
        val secretKeyEntry = keystore.getEntry("MyKeyStore", null) as KeyStore.SecretKeyEntry
        return secretKeyEntry.secretKey
    }

    fun decryptData(ivBytes: ByteArray, data: ByteArray): String{
        val cipher = Cipher.getInstance("AES/CBC/NoPadding")
        val spec = IvParameterSpec(ivBytes)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), spec)
        return cipher.doFinal(data).toString(Charsets.UTF_8).trim()
    }
}
