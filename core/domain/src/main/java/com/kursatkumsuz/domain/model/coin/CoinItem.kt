package com.kursatkumsuz.domain.model.coin


data class CoinItem(
    val id: Int,
    val name: String,
    val quote: Quote,
    val symbol: String
)