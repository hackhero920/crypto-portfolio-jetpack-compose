package com.kursatkumsuz.managecryptoportfolio.presentation.screens.welcome

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.auth.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val isLoggedInUseCase: IsLoggedInUseCase
) : ViewModel() {

    private var _isLoggedInState = mutableStateOf(false)
    val isLoggedInState = _isLoggedInState


    init {
        isLoggedIn()
    }
    private fun isLoggedIn() {
        viewModelScope.launch {
            isLoggedInUseCase.invoke().collect {
                _isLoggedInState.value = it
            }
        }
    }
}