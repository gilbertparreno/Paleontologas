package com.zigis.paleontologas.application.data

import androidx.sqlite.db.SimpleSQLiteQuery
import java.lang.reflect.ParameterizedType
import java.util.*

abstract class BaseRepository<T> constructor(
    private val dao: BaseDao<T>
) {
    abstract suspend fun initialize()

    suspend fun findOne(id: Int): T {
        return dao.findOne(
            SimpleSQLiteQuery(
                "SELECT * FROM ${databaseName()}  WHERE id=$id"
            )
        )
    }

    suspend fun findAll(): List<T> {
        return dao.findAll(
            SimpleSQLiteQuery(
                "SELECT * FROM ${databaseName()}"
            )
        )
    }

    suspend fun findAll(query: SimpleSQLiteQuery): List<T> {
        return dao.findAll(query)
    }

    protected suspend fun deleteAll(): Int {
        return dao.deleteAll(
            SimpleSQLiteQuery(
                "DELETE FROM ${databaseName()}"
            )
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun databaseName(): String {
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        val classType = parameterizedType.actualTypeArguments[0] as Class<T>
        return classType.simpleName.toLowerCase(Locale.getDefault())
    }
}