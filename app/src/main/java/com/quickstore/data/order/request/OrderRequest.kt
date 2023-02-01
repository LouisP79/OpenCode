package com.quickstore.data.order.request

import com.fasterxml.jackson.annotation.JsonProperty

class OrderRequest(@field:JsonProperty var date: String,
                   @field:JsonProperty("delivery_date") var deliveryDate: String,
                   @field:JsonProperty("delivery_time") var deliveryTime: String,
                   @field:JsonProperty("delivery_cost") var deliveryCost: Double,
                   @field:JsonProperty("meeting_point_tag") var meetingPointTag: String,
                   @field:JsonProperty("meeting_point_address") var meetingPointAddress: String,
                   @field:JsonProperty("products") var orderDetails: List<OrderDetailRequest>)