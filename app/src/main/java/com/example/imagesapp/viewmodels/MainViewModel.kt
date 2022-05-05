package com.example.imagesapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.imagesapp.data.Repository
import com.example.imagesapp.data.database.ImagesEntity
import com.example.imagesapp.models.Images
import com.example.imagesapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** Room Database*/

    val readImages : LiveData<List<ImagesEntity>> = repository.local.readDatabase().asLiveData()

    private fun insertImages(imagesEntity: ImagesEntity) =
        viewModelScope.launch (Dispatchers.IO){
            repository.local.insertImages(imagesEntity)
        }

    /** Retrofit */
     var imagesResponse: MutableLiveData<NetworkResult<Images>> = MutableLiveData()
     var searchImagesResponse: MutableLiveData<NetworkResult<Images>> = MutableLiveData()

    fun getImages(queries: Map<String,String>) = viewModelScope.launch {
        getImagesSafeCall(queries)
    }

    fun searchImages(searchQueries: Map<String,String>) = viewModelScope.launch {
        searchImagesSafeCall(searchQueries)
    }

    private suspend fun searchImagesSafeCall(searchQueries: Map<String, String>) {
        searchImagesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()){

            try {
                val response = repository.remote.searchImages(searchQueries)
                searchImagesResponse.value = handleImagesResponse(response)

            }catch (e: Exception){
                searchImagesResponse.value = NetworkResult.Error("Images not Found.")

            }

        }else{
            searchImagesResponse.value = NetworkResult.Error("No Internet Connection.")
        }


    }

    private suspend fun getImagesSafeCall(queries: Map<String, String>) {
        imagesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()){

            try {
                val response = repository.remote.getImages(queries)
                imagesResponse.value = handleImagesResponse(response)

                val images = imagesResponse.value!!.data
                if (images != null){
                    offlineCacheImages(images)
                }
            }catch (e: Exception){
                imagesResponse.value = NetworkResult.Error("Images not Found.")

            }

        }else{
            imagesResponse.value = NetworkResult.Error("No Internet Connection.")
        }

    }

    private fun offlineCacheImages(images: Images) {
        val imagesEntity = ImagesEntity(images)
        insertImages(imagesEntity)
    }

    private fun handleImagesResponse(response: Response<Images>): NetworkResult<Images>? {

        when{
            response.message().toString().contains("timeout") ->{
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 ->{
                return NetworkResult.Error("API key Limited.")
            }

            response.isSuccessful ->{
                val images = response.body()
                return NetworkResult.Success(images!!)
            }
            else ->{
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }
}