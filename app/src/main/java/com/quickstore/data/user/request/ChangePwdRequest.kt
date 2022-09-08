package com.quickstore.data.user.request

import com.fasterxml.jackson.annotation.JsonProperty

class ChangePwdRequest(@field:JsonProperty var oldPass: String,
                       @field:JsonProperty var newPass: String)