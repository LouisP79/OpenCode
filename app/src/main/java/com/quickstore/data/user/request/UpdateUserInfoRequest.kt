package com.quickstore.data.user.request

import com.fasterxml.jackson.annotation.JsonProperty

class UpdateUserInfoRequest(@field:JsonProperty("last_name") var lastName: String,
                            @field:JsonProperty var name: String,
                            @field:JsonProperty var phone: String)