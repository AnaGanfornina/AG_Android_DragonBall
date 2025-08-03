package com.keepcoding.agdragonball.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.keepcoding.agdragonball.databinding.FragmentHeroListBinding
import com.keepcoding.agdragonball.ui.home.HomeViewModel

class FragmentHeroes: Fragment() {
    private lateinit var binding: FragmentHeroListBinding
    // accedemos al vm compartido
    private val viewModel: HomeViewModel by activityViewModels()

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
        /*
        hacer cuando pulsemos un botón del adapter
        binding.root.setOnClickListener{
            onClick()
        }

         */


    }


}