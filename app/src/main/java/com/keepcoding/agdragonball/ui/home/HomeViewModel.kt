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

        _selectedHero.value?.let { selected ->
            val damage = (5..30).random()
            val updatedHero = selected.copy(life = selected.life + 20)

            // Actualizar selectedHero
            _selectedHero.value = updatedHero

            // Actualizar lista de héroes
            _homeState.value.let { state ->
                if (state is HomeState.HeroDownloaded) {
                    val updatedList = state.heroes.map { hero ->
                        if (hero.id == updatedHero.id) updatedHero else hero
                    }
                    _homeState.value = HomeState.HeroDownloaded(updatedList)
                }
            }
        }
    }


        /*
        val updatedHero = hero.copy(life = minOf(hero.life + 20, 100))
        _selectedHero.value = updatedHero

        */





    fun punchHero() {
        _selectedHero.value?.let { selected ->
            val damage = (5..30).random()
            val updatedHero = selected.copy(life = maxOf(selected.life - damage, 0))

            // Actualizar selectedHero
            _selectedHero.value = updatedHero

            // Actualizar lista de héroes
            _homeState.value.let { state ->
                if (state is HomeState.HeroDownloaded) {
                    val updatedList = state.heroes.map { hero ->
                        if (hero.id == updatedHero.id) updatedHero else hero
                    }
                    _homeState.value = HomeState.HeroDownloaded(updatedList)
                }
            }
        }
    }


    //Función para hacer logout

}