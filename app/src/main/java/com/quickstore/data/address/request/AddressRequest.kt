package com.quickstore.data.address.request

import com.fasterxml.jackson.annotation.JsonProperty

class AddressRequest(@field:JsonProperty var tag: String,
                     @field:JsonProperty var address: String,
                     @field:JsonProperty var reference: String,
                     @field:JsonProperty var idDistrict: Long)