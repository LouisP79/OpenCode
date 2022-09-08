package com.quickstore.data.address.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.quickstore.data.district.model.DistrictModel

@JsonIgnoreProperties(ignoreUnknown = true)
class AddressModel{

    @JsonProperty
    var id: Long = 0

    @JsonProperty
    var tag: String = ""

    @JsonProperty
    var address: String = ""

    @JsonProperty
    var reference: String = ""

    @JsonProperty
    var district = DistrictModel()

    @JsonProperty
    var status: Boolean = false

    var selected: Boolean = false

}
