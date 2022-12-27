package com.quickstore.data.cart.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CartModel {

    @field:JsonProperty
    var items = listOf<CartItemsModel>()

    @field:JsonProperty
    var total: Double = 0.0

}
