package com.quickstore.data.user.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author SudTechnologies
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class UserModel{

    @JsonProperty
    var id: Long = 0

    @JsonProperty
    var email: String = ""

    @JsonProperty
    var name: String = ""

    @JsonProperty("last_name")
    var lastName: String? = ""

    @JsonProperty
    var phone: String = ""

}
