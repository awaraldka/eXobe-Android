package com.exobe.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewScrollListener(private val layoutManager: LinearLayoutManager, private val onLoadMore: () -> Unit) : RecyclerView.OnScrollListener() {
    private val visibleThreshold = 8 // The number of items from the end of the list to start loading more items
    private var loading = false

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            // End has been reached, load more data here (pagination)
            // Set loading flag to prevent multiple calls while data is being loaded
            setLoading(true)

            // Call the onLoadMore callback to trigger pagination
            onLoadMore()

            // After loading is complete, set loading flag to false
            setLoading(false)
        }
    }
}
