package com.quickstore.data.cart.request

import com.fasterxml.jackson.annotation.JsonProperty

class AddCartRequest(@field:JsonProperty var productId: Long,
                     @field:JsonProperty var quantity: Double)