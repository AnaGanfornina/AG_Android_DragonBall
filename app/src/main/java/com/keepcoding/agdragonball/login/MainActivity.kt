package com.keepcoding.agdragonball.login

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.google.gson.Gson
import com.keepcoding.agdragonball.databinding.ActivityMainBinding
import com.keepcoding.agdragonball.domain.User
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

        }
        //ListActivity.startActivity(this)

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