package com.reloia.libermobile.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reloia.libermobile.model.BookItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiscoverScreenViewModel(private val repository: DiscoverScreenRepository) : ViewModel() {
    private val _bookItem = MutableStateFlow<List<BookItemData>?>(null)
    val bookItem: StateFlow<List<BookItemData>?> = _bookItem.asStateFlow()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _bookItem.value = repository.getDiscoverData()
            } catch (e: Exception) {
                e.printStackTrace()
                // TODO: Handle errors appropriately
            }
        }
    }
}