package com.keepcoding.agdragonball.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.keepcoding.agdragonball.databinding.ItemHeroCellBinding

class AdapterHero(
    private  val onItemClicked: AdapterView.OnItemClickListener
) : RecyclerView.Adapter<AdapterHero.HeroViewHolder>() {

    private var lista: List<String> = emptyList()

    fun actualizarDatos(nuevosElementos: List<String>){
        lista = nuevosElementos
        notifyDataSetChanged() // "fuerza" a actualizar la lista indicando que los
        // elementos han cambiado
    }

    // esto es qué estamos pintando, qué es ese elemento a pinar
    inner class HeroViewHolder(private val binding: ItemHeroCellBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String) {
            binding.tvHero.text= name


            // Seleccion de celda
            binding.root.setOnClickListener {
                //Cuando pulsen en este elemento haz algo
                Toast.makeText(binding.root.context,name, Toast.LENGTH_LONG).show()

                //onItemClicked.onItemClick(elementoAPintar)
            }
        }
    }

    // cuando vaya a pintar de donde saco esas MainVewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(
            ItemHeroCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    //cuantos elementos tiene la lista
    override fun getItemCount(): Int {
        return lista.size
    }

    //teniendo una vista y la posición de la lista, qué es lo que tengo que pintar?
    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {

        holder.bind(lista[position])
    }
}