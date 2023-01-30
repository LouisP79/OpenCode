package com.quickstore.data.order.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class OrderModel {

    @field:JsonProperty
    var id: Long = 0

    @field:JsonProperty("delivery_date")
    var deliveryDate: String = ""

    @field:JsonProperty("delivery_time")
    var deliveryTime: String = ""

    @field:JsonProperty
    var status: Int = 0

    @field:JsonProperty("products")
    var details = listOf<OrderDetailModel>()

    @field:JsonProperty
    var total: Double = 0.0

}
