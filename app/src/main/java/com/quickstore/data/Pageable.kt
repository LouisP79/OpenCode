package com.quickstore.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
/**
 * @author SudTechnologies
 */
@JsonIgnoreProperties(ignoreUnknown = true)
open class Pageable<T>{

    @JsonProperty
    var items: MutableList<T> = mutableListOf()

    @JsonProperty
    var page: Int = 0

    @JsonProperty("last")
    var isLast: Boolean = false

}
