package com.reloia.libermobile.ui.bookinformation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reloia.libermobile.model.BookItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookInformationViewModel(private val repository: BookInformationRepository) : ViewModel() {
    private val _bookItem = MutableStateFlow<BookItemData?>(null)
    val bookItem: StateFlow<BookItemData?> = _bookItem.asStateFlow()

    init {
        loadBookInformation()
    }

    private fun loadBookInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _bookItem.value = repository.getBookInformation()
            } catch (e: Exception) {
                // TODO: Handle errors appropriately
            }
        }
    }
}
