package com.quickstore.data.firebaseToken.request

import com.fasterxml.jackson.annotation.JsonProperty

class FirebaseTokenRequest(@field:JsonProperty("firebase_token") var firebaseToken: String)