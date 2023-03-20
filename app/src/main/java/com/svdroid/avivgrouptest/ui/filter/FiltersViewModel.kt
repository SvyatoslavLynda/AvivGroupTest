package com.svdroid.avivgrouptest.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svdroid.avivgrouptest.data.repository.PropertiesRepository
import com.svdroid.avivgrouptest.data.ui.UIFilters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiltersViewModel @Inject constructor(
    private val repository: PropertiesRepository,
) : ViewModel() {
    private val _filterRanges = MutableStateFlow<UIFilters?>(null)
    val filterRanges = _filterRanges.asStateFlow()

    private val _filters = MutableStateFlow<UIFilters?>(null)
    var filters = _filters.asStateFlow()

    fun seekPrice(min: Float, max: Float) {
        _filters.value = _filters.value?.copy(minPrice = min.toDouble(), maxPrice = max.toDouble())
    }

    fun seekArea(min: Float, max: Float) {
        _filters.value = _filters.value?.copy(minArea = min.toDouble(), maxArea = max.toDouble())
    }

    fun seekRooms(min: Float, max: Float) {
        _filters.value = _filters.value?.copy(minRooms = min.toInt(), maxRooms = max.toInt())
    }

    fun seekBedrooms(min: Float, max: Float) {
        _filters.value = _filters.value?.copy(minBedrooms = min.toInt(), maxBedrooms = max.toInt())
    }

    fun getFilters(filters: UIFilters?) {
        viewModelScope.launch(Dispatchers.IO) {
            val filterRanges = repository.getFiltersRanges()
            _filterRanges.update { filterRanges }
            _filters.update { filters ?: filterRanges }
        }
    }
}