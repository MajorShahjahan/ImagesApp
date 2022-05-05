package com.example.imagesapp.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagesapp.R
import com.example.imagesapp.adapters.ImagesAdapter
import com.example.imagesapp.bindingadapters.ImagesRowBinding
import com.example.imagesapp.util.NetworkListener
import com.example.imagesapp.util.NetworkResult
import com.example.imagesapp.util.observeOnce
import com.example.imagesapp.viewmodels.ImagesViewModel
import com.example.imagesapp.viewmodels.MainViewModel
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var imagesViewModel: ImagesViewModel
    private val mAdapter by lazy { ImagesAdapter() }
    private lateinit var recyclerView: ShimmerRecyclerView
    private lateinit var networkListener: NetworkListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        imagesViewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)
        ImagesRowBinding.Companion.setContext(this)
        recyclerView = findViewById(R.id.recyclerview)
        setupRecyclerView()
        readDatabase()
        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(applicationContext)
                .collect { status ->
                    imagesViewModel.networkStatus = status
                    imagesViewModel.showNetworkStatus()

                }
        }
    }


    private fun setupRecyclerView() {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        showShimmer()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.images_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readImages.observeOnce(this@MainActivity, { database ->
                if (database.isNotEmpty()) {
                    Log.d("myData", "request from database")
                    mAdapter.setData(database[0].images)
                    hideShimmer()
                } else {
                    requestApiData()
                }

            })
        }
    }

    private fun requestApiData() {
        Log.d("myData", "request from Api")
        mainViewModel.getImages(imagesViewModel.applyQueries())
        mainViewModel.imagesResponse.observe(this, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromCache()
                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmer()
                }
            }

        })
    }

    private fun searchApiData(searchQuery: String) {
        showShimmer()
        mainViewModel.searchImages(imagesViewModel.applySearchQueries(searchQuery))
        mainViewModel.searchImagesResponse.observe(this, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromCache()
                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmer()
                }
            }

        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readImages.observe(this@MainActivity, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].images)
                }

            })
        }
    }

    private fun showShimmer() {
        recyclerView.showShimmer()
    }

    private fun hideShimmer() {
        recyclerView.hideShimmer()
    }

}