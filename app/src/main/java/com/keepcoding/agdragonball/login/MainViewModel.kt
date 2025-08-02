package com.keepcoding.agdragonball.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.agdragonball.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository,
): ViewModel() {

    // El vm se comunica con la Activity mediante un stateFlow

    sealed class MainState{
        data object Idle: MainState()
        data object LoginSuccessful: MainState()
        data class HeroDownloaded(var heroes: List<Hero>): MainState()
        data class Error(val message: String): MainState()
        data object Loading: MainState()
    }

    private val _mainState = MutableStateFlow<MainState>(MainState.Idle)
    val mainState: StateFlow<MainState> = _mainState

    fun performLogin(usuario: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) { //lanzamos la request en segundo plano
            _mainState.update {
                MainState.Loading
            }
            val response = userRepository.performLoginRequest(User(usuario, pass))
            /*
            when (response) {
                is UserRepository.LoginResponse.Success -> {
                    downloadHeros(response.token)
                }
                is UserRepository.LoginResponse.Error -> {
                    _mainState.update {
                        MainState.Error(response.message)
                    }
                }
            }
            */
        }
    }
}

