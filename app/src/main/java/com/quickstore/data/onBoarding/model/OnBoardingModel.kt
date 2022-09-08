package com.quickstore.data.onBoarding.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class OnBoardingModel {

    @field:JsonProperty
    var title: String? = ""

    @field:JsonProperty
    var image: String? = ""

    @field:JsonProperty
    var message: String? = ""

}
