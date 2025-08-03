package com.keepcoding.agdragonball.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

import com.keepcoding.agdragonball.databinding.FragmentHeroDetailBinding
import com.keepcoding.agdragonball.databinding.FragmentHeroListBinding
import com.keepcoding.agdragonball.domain.entities.Hero
import com.keepcoding.agdragonball.ui.home.HomeViewModel
import com.keepcoding.agdragonball.ui.home.adapter.AdapterHero
import kotlinx.coroutines.launch


class FragmentHeroDetail: Fragment()  {


    private lateinit var binding: FragmentHeroDetailBinding
    // accedemos al vm compartido
    private val viewModel: HomeViewModel by activityViewModels()

    // Lambda para comunicar a la Activity
    var onItemClickListener: (Hero) -> Unit = {}



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeroDetailBinding.inflate(inflater, container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Cuando ya esté creada la vista, que va a hacer ?
        super.onViewCreated(view, savedInstanceState)


        setObservers()
        setListeners()

    }

    fun setListeners() {

        binding.bHeal.setOnClickListener {
            viewModel.healHero()
        }

        binding.bPunch.setOnClickListener {
            viewModel.punchHero()
        }




    }

    fun setObservers() {

    //observamos el heroe seleccionado del vm
        viewLifecycleOwner.lifecycleScope.launch {
        viewModel.selectedHero.collect { hero ->
            hero?.let {
                Glide.with(binding.imageHero.context)
                    .load(it.foto)
                    .centerCrop()
                    .into(binding.imageHero)




                binding.detailProgresBarVida.progress = hero.life
            }

        }

    }
}






}