package com.keepcoding.agdragonball.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.keepcoding.agdragonball.databinding.ItemHeroCellBinding
import com.keepcoding.agdragonball.domain.entities.Hero

class AdapterHero(
    private val onItemClicked: (Hero) -> Unit
) : RecyclerView.Adapter<AdapterHero.HeroViewHolder>() {

    private var lista: List<Hero> = emptyList()

    fun actualizarDatos(nuevosElementos: List<Hero>){
        lista = nuevosElementos
        notifyDataSetChanged() // "fuerza" a actualizar la lista indicando que los
        // elementos han cambiado
    }

    // esto es qué estamos pintando, qué es ese elemento a pinar
    inner class HeroViewHolder(private val binding: ItemHeroCellBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: Hero) {

            with(binding){
                tvHero.text = hero.name
                if( hero.isAlive()){
                    Glide.with(binding.shapeableImageView.context)
                        .load(hero.foto)
                        .centerCrop()
                        .into(binding.shapeableImageView)
                    progresBarVida.progress = hero.life
                    root.alpha = 1.0f
                    root.isClickable = true
                }else{
                    Glide.with(shapeableImageView.context)
                        .load(hero.foto)
                        .centerCrop()
                        .into(shapeableImageView)
                    root.alpha = 0.5f
                    root.isClickable = false
                    progresBarVida.progress = hero.life

                }
            }

            if (hero.isAlive()) {
                binding.root.setOnClickListener {
                    onItemClicked(hero)
                }
            } else {
                binding.root.setOnClickListener(null)
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