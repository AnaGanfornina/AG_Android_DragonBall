package com.keepcoding.agdragonball.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.keepcoding.agdragonball.databinding.FragmentHeroListBinding
import com.keepcoding.agdragonball.domain.entities.Hero
import com.keepcoding.agdragonball.ui.home.HomeViewModel
import com.keepcoding.agdragonball.ui.home.adapter.AdapterHero
import kotlinx.coroutines.launch


class FragmentHeroes: Fragment()  {

    private lateinit var heroAdapter: AdapterHero
    private lateinit var binding: FragmentHeroListBinding
    // accedemos al vm compartido
    private val viewModel: HomeViewModel by activityViewModels()

    // Lambda para comunicar a la Activity
    var onItemClickListener: (Hero) -> Unit = {}



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeroListBinding.inflate(inflater, container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Cuando ya esté creada la vista, que va a hacer ?
        super.onViewCreated(view, savedInstanceState)

        heroAdapter = AdapterHero { hero ->
            // Cuando el adapter detecta click, notificamos a la activity
            onItemClickListener(hero)
        }
        binding.rvList.adapter = heroAdapter // le decimos al recycler view que use el heroAadapter
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())


        setObservers()
        //setListeners()

    }
/*
    fun setListeners() {
        binding.root.setOnClickListener {
            val lista  = viewModel.downloadHeros()
            heroAdapter.actualizarDatos(lista)
        }
    }
*/
    fun setObservers() {

    //observamos los datos de la lista del vm
    lifecycleScope.launch {
        viewModel.homeState.collect { state ->
            when(state) {
                is HomeViewModel.HomeState.Idle -> {
                    //binding.progressBar.visibility = View.GONE
                    //binding.tvError.visibility = View.GONE
                }
                is HomeViewModel.HomeState.Loading -> {
                    //binding.progressBar.visibility = View.VISIBLE
                    //binding.tvError.visibility = View.GONE
                }
                is HomeViewModel.HomeState.HeroDownloaded -> {
                    //binding.progressBar.visibility = View.GONE
                    //binding.tvError.visibility = View.GONE
                    heroAdapter.actualizarDatos(state.heroes)
                }
                is HomeViewModel.HomeState.Error -> {
                   // binding.progressBar.visibility = View.GONE
                   //binding.tvError.visibility = View.VISIBLE
                    //binding.tvError.text = state.message
                }
            }
        }
    }

    }





}