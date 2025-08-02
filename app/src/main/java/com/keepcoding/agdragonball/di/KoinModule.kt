package com.keepcoding.agdragonball.di

import com.keepcoding.agdragonball.login.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

// Declaramos viewModels
 val viewModelModule = module {
     viewModel { MainViewModel(get()) }
}