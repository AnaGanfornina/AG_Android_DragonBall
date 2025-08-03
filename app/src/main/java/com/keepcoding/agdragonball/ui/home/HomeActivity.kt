package com.keepcoding.agdragonball.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.keepcoding.agdragonball.databinding.ActivityHomeBinding
import com.keepcoding.agdragonball.databinding.ActivityMainBinding
import com.keepcoding.agdragonball.domain.entities.Hero
import com.keepcoding.agdragonball.ui.home.adapter.AdapterHero
import com.keepcoding.agdragonball.ui.home.fragments.FragmentHeroes



class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    //Accedemos al VM compartido
    private val viewModel: HomeViewModel by viewModel()

    //Inicializamos la activity
    companion object{
        fun startActivity(context: Context,token: String){
            val intent = Intent(context,HomeActivity::class.java)
            intent.putExtra("token", token)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        intent.extras?.let {
            val token = it.getString("token","sin token")
            viewModel.downloadHeros(token)
        }


        // Creación del Fragment

        val fragment = FragmentHeroes()
        fragment.onItemClickListener = { name ->
            // Aquí recibes el click y haces la acción, p.ej cambiar fragment
            Toast.makeText(this, "Has pulsado: $name", Toast.LENGTH_SHORT).show()
/*
            // O navegar a otro fragmento:
            supportFragmentManager.beginTransaction()
                .replace(binding.flList.id,FragmetnDetail(name))
                .addToBackStack(null)
                .commit()

 */
        }


        supportFragmentManager.beginTransaction()
            .replace(binding.flList.id,fragment)
            .commit()

    }


}