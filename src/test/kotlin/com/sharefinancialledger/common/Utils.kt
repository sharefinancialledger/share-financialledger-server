package com.sharefinancialledger.common
import org.mockito.ArgumentMatchers

fun <T> argThat(matcher: (arg: T) -> Boolean): T {
    ArgumentMatchers.argThat(matcher)
    return castNull()
}

fun <T> eq(value: T): T {
    ArgumentMatchers.eq(value)
    return value
}

inline fun <reified T> createInstance(): T = when (T::class) {
    Int::class -> 0 as T
    Set::class -> emptySet<Any>() as T
    List::class -> emptyList<Any>() as T
    else -> castNull()
}

fun <T> castNull(): T {
    @Suppress("UNCHECKED_CAST")
    return null as T
}