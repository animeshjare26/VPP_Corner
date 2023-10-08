package com.vpppcoe.vppcorner.Model

data class Orders(
    var addition: String? = " ",
    var amount: Int = 0,
    var items: String? = " ",
    var order_number: Int = 0,
    var order_time: String? = " ",
    val paymentSuccess: Boolean = true,
    var email : String = " ",
    var id : String = " ",
    var noti : Boolean = false
)
