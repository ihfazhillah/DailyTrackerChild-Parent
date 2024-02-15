package com.ihfazh.dailytrackerchild_parent.fp

data class OutcomeError(
    val msg: String
)

sealed class Outcome<out T, out E: OutcomeError>{
    fun <U> transform(f: (T) -> U): Outcome<U, E> =
        when(this){
            is Success -> Success(f(value))
            is Failure -> this
        }

    companion object{
        fun <T> success(data: T): Success<T> {
            return Success(data)
        }

        fun <E: OutcomeError> failure(error: E): Failure<E> {
            return Failure(error)
        }
    }

}

inline fun  <E: OutcomeError, T> Outcome<T, E>.recover(recoverError: (OutcomeError) -> T): T =
    when(this){
        is Success -> value
        is Failure -> recoverError(error)
    }

data class Success<T> internal constructor(val value: T): Outcome<T, Nothing>()
data class Failure<E: OutcomeError> internal constructor(val error: OutcomeError): Outcome<Nothing, E>()


