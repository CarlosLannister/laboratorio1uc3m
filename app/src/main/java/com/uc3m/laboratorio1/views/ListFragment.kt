package com.uc3m.laboratorio1.views

import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
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
import java.lang.Exception
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView
            (inflater: LayoutInflater,
             container: ViewGroup?,
             savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        binding.user.text = currentUser?.displayName

        studentViewModel = ViewModelProvider(this).get(StudentViewModel::class.java)

        if (!studentViewModel.checkKey()){
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec
                    .Builder("MyKeyStore", KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()

            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }

        /*
          https://stackoverflow.com/questions/992019/java-256-bit-aes-password-based-encryption

          SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
          KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
          SecretKey tmp = factory.generateSecret(spec);
          SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
         */

        val adapter = ListAdapter(studentViewModel)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
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
