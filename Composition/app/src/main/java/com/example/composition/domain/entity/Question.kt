package com.example.composition.domain.entity

data class Question(
    val sum : Int,
    val visibleNubmer : Int,
    val option : List<Int>
){

    val rightAnswer : Int
        get() = sum-visibleNubmer

}