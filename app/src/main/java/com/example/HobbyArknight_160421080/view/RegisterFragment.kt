package com.example.HobbyArknight_160421080.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.android.volley.toolbox.Volley
import com.example.HobbyArknight_160421080.databinding.FragmentRegisterBinding
import com.example.HobbyArknight_160421080.model.User
import com.example.HobbyArknight_160421080.view.MainActivity.Companion.user
import com.example.HobbyArknight_160421080.viewModel.UserViewModel
import org.json.JSONObject


class RegisterFragment : Fragment() {
    private lateinit var bind: FragmentRegisterBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return bind.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).DisableNavBar()


        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        bind.btnRegister.setOnClickListener {
            val username = bind.txtUsernameRegister.text.toString()
            val pass = bind.txtPasswordRegister.text.toString()
            val depan = bind.txtNamaDepan.text.toString()
            val belakang = bind.txtNamaBelakang.text.toString()
            val email = bind.txtEmail.text.toString()
            val user = User(username, pass, depan, belakang, email)

            //pengecheckan (tidak boleh kosong)
            if (bind.txtUsernameRegister.text.toString().trim().isEmpty()) {
                bind.txtUsernameRegister.error = "Username cannot be empty"
            } else if (bind.txtNamaDepan.text.toString().trim().isEmpty()) {
                bind.txtNamaDepan.error = "Nama Depan cannot be empty"
            } else if (bind.txtNamaBelakang.text.toString().trim().isEmpty()) {
                bind.txtNamaBelakang.error = "Nama Belakang cannot be empty"
            } else if (bind.txtEmail.text.toString().trim().isEmpty()) {
                bind.txtEmail.error = "Email cannot be empty"
            } else if (bind.txtPasswordRegister.text.toString().trim().isEmpty()) {
                bind.txtPasswordRegister.error = "Password cannot be empty"
            } else {
                viewModel.register(user)
                observeViewModel()
            }

        }

        bind.txtSignIn.setOnClickListener {
            val action = RegisterFragmentDirections.signinFragmentAction()
            Navigation.findNavController(it).navigate(action)
        }
    }


    fun observeViewModel(){
//        viewModel.userErrorLD.observe(viewLifecycleOwner) { userError ->
//            if (userError) {
//                Toast.makeText(requireContext(), "Username already exists", Toast.LENGTH_SHORT).show()
//            } else {
//                viewModel.register(user)
//            }
//        }



        viewModel.successLD.observe(viewLifecycleOwner, Observer {
            if (it != false){
                Toast.makeText(this.context, "Akun berhasil dibuat, silahkan login ", Toast.LENGTH_SHORT).show()
                val action = RegisterFragmentDirections.signinFragmentAction()
                Navigation.findNavController(requireView()).navigate(action)
            }
            else{
                val dialog = AlertDialog.Builder(activity)
                dialog.setTitle("Informasi")
                dialog.setMessage("Gagal mendaftarkan akun.\nUsername telah terdaftar, Silahkan Login")
                dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                dialog.create().show()

//                Toast.makeText(this.context, "Akun gagal, terdapat username yang sama", Toast.LENGTH_SHORT).show()
                val action = RegisterFragmentDirections.signinFragmentAction()
                Navigation.findNavController(requireView()).navigate(action)
            }
        })
    }

}