package com.keepcoding.agdragonball.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

interface OnItemClickListener{
    fun onItemClick(texto: String)
}


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
        /*
        setAdapter(lista) //le paso la lista inicial al adapter
        setListeners() //escucho si alguien ha puslado el botón para añaidr nuevos elementos

         */
        binding.flList

        supportFragmentManager.beginTransaction()
            .replace(binding.flList.id,FragmentHeroes())
            .commit()

    }


    override fun onItemClick(texto: String) {
        // Enviar info de celda pulsada
    }


    private fun setListeners(){

        mainAdapter.actualizarDatos(lista)

    }
    private fun setAdapter(lista: List<String>){
        with(binding.rvList) {
            //binding.rvMain.layoutManager = LinearLayoutManager(this)
            //binding.rvMain.adapter = AdapterMain()

            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = mainAdapter
            mainAdapter.actualizarDatos(lista)
        }
    }



}