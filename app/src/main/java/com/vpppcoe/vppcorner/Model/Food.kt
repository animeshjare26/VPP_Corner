package com.vpppcoe.vppcorner.Model

data class Food (
    var available: Boolean = true,
    var name: String? = null,
    var price: Int = 0,
    var time: String? = null,
    var image: String? = null
        )