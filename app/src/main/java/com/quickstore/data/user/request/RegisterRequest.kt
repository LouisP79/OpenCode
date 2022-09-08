package com.quickstore.data.user.request

import com.fasterxml.jackson.annotation.JsonProperty

class RegisterRequest(@field:JsonProperty var email: String,
                      @field:JsonProperty var password: String,
                      @field:JsonProperty var name: String,
                      @field:JsonProperty var lastName: String,
                      @field:JsonProperty var phone: String)