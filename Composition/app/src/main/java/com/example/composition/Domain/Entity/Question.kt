package com.example.composition.Domain.Entity

data class Question(
    val sum : Int,
    val visibleNubmer : Int,
    val option : List<Int>
) {
}