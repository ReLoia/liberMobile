package com.reloia.libermobile.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reloia.libermobile.model.BookItemData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LibraryScreenViewModel(private val repository: LibraryScreenRepository) : ViewModel() {
    private val _bookItem = MutableStateFlow<List<BookItemData>?>(null)
    val bookItem: StateFlow<List<BookItemData>?> = _bookItem.asStateFlow()

    init {
        loadRecentItems()
    }

    private fun loadRecentItems() {
        viewModelScope.launch {
            try {
                _bookItem.value = repository.getLibraryItems()
            } catch (e: Exception) {
                // TODO: Handle errors appropriately
            }
        }
    }
}
