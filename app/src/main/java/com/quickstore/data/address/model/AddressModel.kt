package com.quickstore.data.address.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class AddressModel{

    @JsonProperty
    var id: Long = 0

    @JsonProperty
    var tag: String = ""

    @JsonProperty
    var address: String = ""

    var selected: Boolean = false
}
