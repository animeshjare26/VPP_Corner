package com.vpppcoe.vppcorner.Model

data class Orders(
    val paymentSuccess: Boolean,
    var order_time: String?,
    var amount: Int = 0,
    var order_number: Int = 0,
    var addition: String? = null,
    var items: String? = null
)
