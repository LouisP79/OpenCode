package com.quickstore.data.order.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class OrderIdModel {

    @field:JsonProperty("order_id")
    var orderId: Long = 0

}
