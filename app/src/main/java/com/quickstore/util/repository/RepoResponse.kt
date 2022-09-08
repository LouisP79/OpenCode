package com.quickstore.util.repository

import retrofit2.Response

class RepoResponse<T>(val dataResponse: Response<T>?, val throwable: Throwable?) {
    companion object {
        fun <T> respond(dataResponse: Response<T>?, throwable: Throwable?): RepoResponse<T> {
            return RepoResponse(dataResponse, throwable)
        }
    }
}