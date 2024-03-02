package com.reloia.libermobile.ui.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecentScreenViewModel(private val repository: RecentScreenRepository) : ViewModel() {

    private val _recentItems = MutableStateFlow<List<RecentItem>?>(null)
    val recentItems: StateFlow<List<RecentItem>?> = _recentItems.asStateFlow()

    init {
        loadRecentItems()
    }

    private fun loadRecentItems() {
        viewModelScope.launch {
            try {
                _recentItems.value = repository.getRecentItems()
            } catch (e: Exception) {
                // TODO: Handle errors appropriately
            }
        }
    }
}
