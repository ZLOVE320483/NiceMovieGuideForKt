package com.zlove.movie.kotlin.utils

import io.reactivex.disposables.Disposable

class RxUtils {
    companion object {
        fun unSubscribe(subscription: Disposable) {
            if (subscription != null && !subscription.isDisposed)
                subscription.dispose()
        }

        fun unSubscribe(vararg subscriptions: Disposable) {
            for (subscription in subscriptions) {
                unSubscribe(subscription)
            }
        }
    }
}