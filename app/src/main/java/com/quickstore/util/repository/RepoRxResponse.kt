package com.quickstore.util.repository

import io.reactivex.disposables.Disposable

class RepoRxResponse<T, X> {
    var flatMapResponse: T? = null
    var subscribeResponse: X? = null
    var throwable: Throwable? = null
    var disposable: Disposable? = null
}