package com.keepcoding.agdragonball.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.agdragonball.domain.entities.Hero
import com.keepcoding.agdragonball.domain.interfaces.HeroRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(
    private val heroRepository: HeroRepositoryInterface,
): ViewModel() {


    // El vm se comunica con la Activity mediante un stateFlow

    sealed class HomeState{
        data object Idle: HomeState()
        data class HeroDownloaded(var heroes: List<Hero>): HomeState()
        data class Error(val message: String): HomeState()
        data object Loading: HomeState()
    }

    private var currentHeroes: MutableList<Hero> = mutableListOf()


    private val _homeState = MutableStateFlow<HomeState>(HomeState.Idle)
    val homeState: StateFlow<HomeState> = _homeState

    private val _selectedHero = MutableStateFlow<Hero?>(null)
    val selectedHero: StateFlow<Hero?> = _selectedHero

    fun selectHero(hero: Hero) {

        _selectedHero.value = hero



    }

    //Función para pasarle los heroes

    fun downloadHeros(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _homeState.update { HomeState.Loading }
            val response = heroRepository.performDownloadHerosRequest(token)
            when (response) {
                is HeroRepositoryInterface.DownloadHeroesResponse.Success -> {

                    //actualizamos el estado
                    currentHeroes = response.heroes.toMutableList()

                    _homeState.update {
                        HomeState.HeroDownloaded(currentHeroes)
                    }
                }
                is HeroRepositoryInterface.DownloadHeroesResponse.Error -> {
                    _homeState.update {
                        HomeState.Error(response.message)
                    }
                }

            }
        }
    }

    fun healHero() {
        _selectedHero.value?.let { hero ->

            val updatedHero = hero.copy(life = minOf(hero.life + 20, 100))
            _selectedHero.value = updatedHero


        }
    }

    fun punchHero() {
        _selectedHero.value?.let { hero ->
            val damage = (5..30).random() // Daño aleatorio entre 5 y 30
            val updatedHero = hero.copy(life = maxOf(hero.life - damage, 0))
            _selectedHero.value = updatedHero
        }
    }

    //Función para hacer logout

}