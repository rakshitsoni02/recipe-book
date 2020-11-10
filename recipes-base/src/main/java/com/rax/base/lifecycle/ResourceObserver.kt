package com.rax.base.lifecycle

import androidx.lifecycle.Observer
import com.rax.base.utils.string.StringProvider

class ResourceObserver<T>(
    val loadingState: ((Boolean) -> Unit)? = null,
    val error: ((StringProvider) -> Unit)? = null,
    val data: ((T) -> Unit)?
) : Observer<ViewState<T>> {
    override fun onChanged(currentValue: ViewState<T>?) {
        when (currentValue) {
            is ViewState.Loading -> {
                loadingState?.invoke(true)
            }
            is ViewState.Error -> {
                loadingState?.invoke(false)
                error?.invoke(currentValue.message)
            }
            is ViewState.Success<*> -> {
                loadingState?.invoke(false)
                @Suppress("UNCHECKED_CAST")
                data?.invoke(currentValue.data as T)
            }
        }
    }
}