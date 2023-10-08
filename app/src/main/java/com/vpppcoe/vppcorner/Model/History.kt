package com.vpppcoe.vppcorner.Model

data class History(
    var order : Int = 0,
    var items : String? = " ",
    var price : Int = 0,
    val time : String? = " ",
    var completion : Boolean = false,
    var noti : Boolean = true
)