package example.com.totalnfl.data.base

abstract class BaseMapper<T, R> {

    abstract fun map(model: T): R

    fun map(list: List<T>): List<R> =
        list.map(::map)
}