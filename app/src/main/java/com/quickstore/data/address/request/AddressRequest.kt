package com.quickstore.data.address.request

import com.fasterxml.jackson.annotation.JsonProperty

class AddressRequest(@field:JsonProperty var tag: String,
                     @field:JsonProperty var address: String)