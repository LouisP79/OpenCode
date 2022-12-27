package com.quickstore.data.cart.request

import com.fasterxml.jackson.annotation.JsonProperty

class AddCartRequest(@field:JsonProperty("product_id") var productId: Long,
                     @field:JsonProperty var quantity: Double)