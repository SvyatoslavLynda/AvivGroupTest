package com.svdroid.avivgrouptest.ui.property

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svdroid.avivgrouptest.data.repository.PropertiesRepository
import com.svdroid.avivgrouptest.data.ui.UIPropertyModel
import com.svdroid.avivgrouptest.data.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class PropertiesViewModel(protected val repository: PropertiesRepository) : ViewModel() {
    protected val _state = MutableStateFlow<DataState<List<UIPropertyModel>>>(DataState.Loading())
    val state = _state.asStateFlow()

    fun toggleFavorite(property: UIPropertyModel) {
        viewModelScope.launch(Dispatchers.IO) {
            @Suppress("UNUSED_VARIABLE") val result = repository.toggleFavorite(property)
            //todo add result handler
        }
    }
}