package com.svdroid.avivgrouptest.ui.property.favorites

import androidx.lifecycle.viewModelScope
import com.svdroid.avivgrouptest.data.repository.PropertiesRepository
import com.svdroid.avivgrouptest.ui.property.PropertiesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesPropertiesListViewModel @Inject constructor(
    repository: PropertiesRepository,
) : PropertiesViewModel(repository) {

    init {
        getFavoriteProperties()
    }

    private fun getFavoriteProperties() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavoriteProperties().collect { result -> _state.value = result }
        }
    }
}