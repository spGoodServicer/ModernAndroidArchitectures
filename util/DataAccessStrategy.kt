package com.example.rickandmorty.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.nereus.craftbeer.util.Resource
import com.nereus.craftbeer.util.Resource.Status.*
import kotlinx.coroutines.Dispatchers

/**
 * Perform get operation
 *
 * @param T
 * @param A
 * @param databaseQuery
 * @param networkCall
 * @param saveCallResult
 * @receiver
 * @receiver
 * @receiver
 * @return
 */
fun <T, A> performGetOperation(databaseQuery: () -> LiveData<T>,
                               networkCall: suspend () -> Resource<A>,
                               saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }

/**
 * Perform get operation db
 *
 * @param T
 * @param databaseQuery
 * @receiver
 * @return
 */
fun <T> performGetOperationDb(databaseQuery: () -> LiveData<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)
    }