package com.vpppcoe.vppcorner.Model

data class Food (
    var available: Boolean = true,
    var name: String? = null,
    var price: Int = 0,
    var time: Int = 0,
    var image: String? = null
        )