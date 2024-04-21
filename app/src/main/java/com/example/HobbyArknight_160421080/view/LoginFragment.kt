package com.example.HobbyArknight_160421080.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.HobbyArknight_160421080.databinding.FragmentLoginBinding
import com.example.HobbyArknight_160421080.viewModel.UserViewModel
import com.squareup.picasso.Picasso

class LoginFragment : Fragment() {

    private lateinit var bind: FragmentLoginBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        bind = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        bind.btnLogin.setOnClickListener {
            val user = bind.txtUsernameLogin.text.toString()
            val pass = bind.txtPasswordLogin.text.toString()

            if(user == "" || pass == ""){
                Toast.makeText(this.context, "Field is empty", Toast.LENGTH_SHORT).show()
            }
            else{
                viewModel.signin(user, pass)
                observeViewModel()
            }
        }

        bind.btnNew.setOnClickListener {
            val action = LoginFragmentDirections.registerFragmentAction()
            Navigation.findNavController(it).navigate(action)
        }

        Picasso.get()
            .load("https://gamepress.gg/arknights/sites/arknights/files/styles/banner_image/public/2022-04/live.jpg?h=8fefd763&itok=nMeKf1nE")
            .into(bind.imgBg);
    }

    fun observeViewModel(){
        viewModel.userLD.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Toast.makeText(this.context, "Selamat Datang di News Arknight", Toast.LENGTH_SHORT).show()
                MainActivity.user = it
                val action = LoginFragmentDirections.homeFragmentAction()
                Navigation.findNavController(requireView()).navigate(action)
            }
            else{
                Toast.makeText(this.context, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        })
    }


}