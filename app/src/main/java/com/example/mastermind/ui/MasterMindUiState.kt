package com.example.mastermind.ui

data class MasterMindUiState (
    val isGameOver: Boolean = false,
    val playerWon: Boolean = false,
    val numberOfGuesses: Int = 0,

)