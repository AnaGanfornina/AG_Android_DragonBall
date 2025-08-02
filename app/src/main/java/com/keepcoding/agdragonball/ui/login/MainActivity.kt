package com.keepcoding.agdragonball.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.keepcoding.agdragonball.databinding.ActivityMainBinding
import com.keepcoding.agdragonball.domain.entities.User
import com.keepcoding.agdragonball.ui.home.HomeActivity
import com.keepcoding.agdragonball.ui.login.MainViewModel.MainState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()
        setObservers()

    }

    fun setListeners() {

        val user = cargarReferences()

        with(binding){
            etUser.setText(user.name)
            etPassword.setText(user.password)

            bLogin.setOnClickListener {
                onLoginClicker()

            }


            etUser.doAfterTextChanged {
                setButtonState()
            }

            etPassword.doAfterTextChanged {
                setButtonState()
            }

            setButtonState()
        }
    }

    fun setObservers() {
        lifecycleScope.launch {
            viewModel.mainState.collect { stade ->
                when(stade) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        // todo: progres barr
                    }
                    is MainState.LoginSuccessful -> {
                        // lanzar la siguiente activity
                        val token = stade.token
                        setToken(token)
                        HomeActivity.startActivity(this@MainActivity,token)
                    }

                    is MainState.Error -> TODO()
                }
            }
        }
        }



    private fun setButtonState(){
        with(binding) {
            bLogin.isEnabled = etPassword.text.toString().isNotBlank() && etUser.text.toString().isNotBlank()
        }
    }
    private fun onLoginClicker(){
        with(binding){
            val user = User(etUser.text.toString(), etPassword.text.toString())
            if (rememberUser.isChecked){
                guardarEnSharePreferences(user)
            }else{
                guardarEnSharePreferences(User())
            }

            viewModel.performLogin("a@gmail.com", "abcdef")
            //viewModel.performLogin(user.name, user.password)



        }




    }

    private fun setToken(token:String){
        getSharedPreferences("MainActivity", MODE_PRIVATE).edit {
            putString("token", token)
        }
    }
    private fun getToken(): String{
        val token = getSharedPreferences("MainActivity", MODE_PRIVATE).getString(
            "token",""
        ).orEmpty()
        return token
    }


    private fun guardarEnSharePreferences(user: User){
        getSharedPreferences("MainActivity", MODE_PRIVATE).edit {
            putString("User", user.toJson())
        }
    }

    private fun cargarReferences() : User {
        val user =  Gson().fromJson(
            getSharedPreferences("MainActivity", MODE_PRIVATE).getString(
                "User", ""
            ), User::class.java
        )
        if (user == null) return User()
        else return user
    }

}