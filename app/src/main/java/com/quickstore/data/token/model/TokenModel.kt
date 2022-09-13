package com.quickstore.data.token.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.quickstore.data.RestConstant

@JsonIgnoreProperties(ignoreUnknown = true)
class TokenModel {

    @JsonProperty("access_token")
    var accessToken: String = ""
        get() = RestConstant.BEARER + field

    @JsonProperty("refresh_token")
    var refreshToken: String = ""
}
