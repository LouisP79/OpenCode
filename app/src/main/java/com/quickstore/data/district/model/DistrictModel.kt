package com.quickstore.data.district.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class DistrictModel{

    @JsonProperty
    var id: Long = 0

    @JsonProperty
    var name: String = ""

    @JsonProperty
    var deliveryCost: Double = 0.0

    @JsonProperty
    var status: Boolean = false

}
