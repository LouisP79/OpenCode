package com.quickstore.data.country.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CountryModel {

    @field:JsonProperty("iso")
    var iso: String = ""

    @field:JsonProperty("nice_name")
    var name: String = ""

    @field:JsonProperty("phone_code")
    var phoneCode: String = ""

}
