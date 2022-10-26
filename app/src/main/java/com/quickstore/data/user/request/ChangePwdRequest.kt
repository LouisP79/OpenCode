package com.quickstore.data.user.request

import com.fasterxml.jackson.annotation.JsonProperty

class ChangePwdRequest(@field:JsonProperty("old_pass") var oldPass: String,
                       @field:JsonProperty("new_pass") var newPass: String)