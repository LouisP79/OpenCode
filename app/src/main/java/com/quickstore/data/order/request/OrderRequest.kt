package com.quickstore.data.order.request

import com.fasterxml.jackson.annotation.JsonProperty

class OrderRequest(@field:JsonProperty("delivery_date") var deliveryDate: String,
                   @field:JsonProperty("delivery_time") var deliveryTime: String,
                   @field:JsonProperty("products") var orderDetails: List<OrderDetailRequest>)