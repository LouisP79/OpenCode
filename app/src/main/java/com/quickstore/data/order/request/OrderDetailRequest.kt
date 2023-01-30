package com.quickstore.data.order.request

import com.fasterxml.jackson.annotation.JsonProperty

class OrderDetailRequest(@field:JsonProperty var description: String,
                         @field:JsonProperty var image: String,
                         @field:JsonProperty var name: String,
                         @field:JsonProperty var price: Double,
                         @field:JsonProperty var quantity: Double)