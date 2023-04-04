package com.ausom.drooler.common

interface ActionWrapper<WrappedAction> {
    val action: WrappedAction
}

inline fun <From, reified To> From.unwrap(): To? =
    if (this !is ActionWrapper<*> || this.action !is To) null
    else this.action as To