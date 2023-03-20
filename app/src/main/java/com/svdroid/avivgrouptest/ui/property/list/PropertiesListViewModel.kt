package com.svdroid.avivgrouptest.ui.property.list

import androidx.lifecycle.viewModelScope
import com.svdroid.avivgrouptest.data.repository.PropertiesRepository
import com.svdroid.avivgrouptest.data.ui.UIFilters
import com.svdroid.avivgrouptest.data.utils.DataState
import com.svdroid.avivgrouptest.ui.property.PropertiesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertiesListViewModel @Inject constructor(
    repository: PropertiesRepository,
) : PropertiesViewModel(repository) {
    fun getProperties(filters: UIFilters?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProperties(filters).collect { result -> _state.value = result }
        }
    }

    fun refreshProperties(filters: UIFilters?) {
        if (_state.value !is DataState.Loading) {
            _state.update { DataState.Loading() }

            getProperties(filters)
        }
    }
}