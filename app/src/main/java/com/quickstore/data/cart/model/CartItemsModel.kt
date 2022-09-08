package com.quickstore.data.cart.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.quickstore.data.product.model.ProductModel

@JsonIgnoreProperties(ignoreUnknown = true)
class CartItemsModel {

    @field:JsonProperty
    var id: Long = 0

    @field:JsonProperty
    var product = ProductModel()

    @field:JsonProperty
    var quantity: Double = 0.0

    @field:JsonProperty
    var total: Double = 0.0

    var loading: Boolean = false

}
