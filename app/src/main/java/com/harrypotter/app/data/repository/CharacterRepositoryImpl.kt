package com.harrypotter.app.data.repository

import android.content.Context
import com.harrypotter.app.ui.ErrorMessage
import com.harrypotter.app.ui.NetworkError
import com.harrypotter.app.ui.NetworkTimeoutError
import com.harrypotter.app.ui.NotFoundError
import com.harrypotter.app.ui.Resource
import com.harrypotter.app.ui.ServerError
import com.harrypotter.app.data.local.room.CharacterDao
import com.harrypotter.app.data.remote.api.ApiService
import com.harrypotter.app.domain.model.Character
import com.harrypotter.app.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.harrypotter.app.ui.UnknownError
import com.harrypotter.app.utils.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterRepositoryImpl(
    private val characterDao: CharacterDao,
    private val apiService: ApiService,
    private val context: Context,
) : CharacterRepository {
    override suspend fun getCharacters(): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading())
        if (isOnline(context)) {
            try {
                val characters = withContext(Dispatchers.IO) {
                    apiService.getCharactersList()
                }
                withContext(Dispatchers.IO) {
                    characterDao.insertAll(characters)
                }

                characterDao.getAllCharacters().map { characterList ->
                    Resource.Success(characterList)
                }.catch { error ->
                    emit(Resource.Error(mapExceptionToErrorMessage(error)))
                }.collect { resource ->
                    emit(resource)
                }
            } catch (error: Exception) {
                if (error is SocketTimeoutException || error is IOException) {
                    characterDao.getAllCharacters().map { characters ->
                        Resource.Success(characters)
                    }.catch { e ->
                        emit(Resource.Error(mapExceptionToErrorMessage(e)))
                    }.collect { resource ->
                        emit(resource)
                    }

                } else {
                    emit(Resource.Error(mapExceptionToErrorMessage(error)))
                }
            }
        } else {
            characterDao.getAllCharacters().map { characters ->
                Resource.Success(characters)
            }.catch { error->
                emit(Resource.Error(mapExceptionToErrorMessage(error)))
            }.collect{
                    resource -> emit(resource)
            }
        }

    }


    private fun mapExceptionToErrorMessage(e: Throwable): ErrorMessage {
        return when (e) {
            is SocketTimeoutException -> NetworkTimeoutError(e.message ?: "Network timeout")
            is UnknownHostException -> NetworkError(e.message ?: "Network error")
            is HttpException -> {
                when (e.code()) {
                    404 -> NotFoundError("Not found")
                    500 -> ServerError("Server error")
                    else -> UnknownError(e.message ?: "Unknown error")
                }
            }
            else -> UnknownError(e.message ?: "Unknown error")
        }
    }
}