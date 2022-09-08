package com.quickstore.data.category.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CategoryModel {

    @field:JsonProperty
    var id: Long = 0

    @field:JsonProperty
    var name: String = ""

    @field:JsonProperty
    var image: String = ""

}
