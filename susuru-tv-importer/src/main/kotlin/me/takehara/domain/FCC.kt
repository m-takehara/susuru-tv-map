package me.takehara.domain

abstract class FCC<T>(private val collection: Collection<T>) {
    fun <S> map(f: (T) -> S) = this.collection.map(f)
}
