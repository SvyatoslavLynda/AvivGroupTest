package com.svdroid.avivgrouptest.ui.property.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svdroid.avivgrouptest.data.repository.PropertiesRepository
import com.svdroid.avivgrouptest.data.ui.UIPropertyModel
import com.svdroid.avivgrouptest.data.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val repository: PropertiesRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<DataState<UIPropertyModel>>(DataState.Loading())

    fun getProperty(propertyId: Long): StateFlow<DataState<UIPropertyModel>> {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProperty(propertyId).collect { result -> _state.value = result }
        }

        return _state.asStateFlow()
    }

    fun toggleFavorite(property: UIPropertyModel) {
        viewModelScope.launch(Dispatchers.IO) {
            @Suppress("UNUSED_VARIABLE") val result = repository.toggleFavorite(property)
            //todo add result handler
        }
    }
}