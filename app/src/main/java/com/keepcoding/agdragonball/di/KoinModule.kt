package com.keepcoding.agdragonball.di

import com.keepcoding.agdragonball.domain.interfaces.HeroRepositoryInterface
import com.keepcoding.agdragonball.domain.interfaces.UserRepositoryInterface
import com.keepcoding.agdragonball.domain.repository.HeroRepository
import com.keepcoding.agdragonball.domain.repository.UserRepository
import com.keepcoding.agdragonball.ui.home.HomeViewModel
import com.keepcoding.agdragonball.ui.login.MainViewModel
import org.koin.core.module.dsl.viewModel

import org.koin.dsl.module


// Declaramos un singleton del repositorio
val repositoryModule = module {
    single<UserRepositoryInterface>{ UserRepository()}
    single<HeroRepositoryInterface> { HeroRepository() }
}

// Declaramos viewModels
 val viewModelModule = module {
     viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}