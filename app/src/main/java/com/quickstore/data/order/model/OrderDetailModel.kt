package com.quickstore.data.order.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class OrderDetailModel {

    @field:JsonProperty
    var description: String = ""

    @field:JsonProperty
    var image: String = ""

    @field:JsonProperty
    var name: String = ""

    @field:JsonProperty
    var price: Double = 0.0

    @field:JsonProperty
    var quantity: Double = 0.0

    @field:JsonProperty("sub_total_per_product")
    var subTotal: Double = 0.0

}
