package com.svdroid.avivgrouptest.ui.filter

import com.nhaarman.mockitokotlin2.whenever
import com.svdroid.avivgrouptest.data.repository.PropertiesRepository
import com.svdroid.avivgrouptest.data.ui.UIFilters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class FiltersViewModelTest {
    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    private lateinit var viewModel: FiltersViewModel
    private lateinit var repository: PropertiesRepository

    @Before
    fun setup() {
        repository = mock(PropertiesRepository::class.java)
        viewModel = FiltersViewModel(repository)
    }

    @Test
    fun seekPriceUpdatesFilters() {
        // Given
        val filters = UIFilters(
            minPrice = 10.0,
            maxPrice = 100.0,
            minArea = 10.0,
            maxArea = 1000.0,
            minRooms = 1,
            maxRooms = 21,
            minBedrooms = 0,
            maxBedrooms = 10,
        )
        viewModel.getFilters(filters)
        val minPrice = 500.0f
        val maxPrice = 1000.0f

        // When
        viewModel.seekPrice(minPrice, maxPrice)

        // Then
        testScope.launch {
            assert(viewModel.filters.first()?.minPrice == minPrice.toDouble())
            assert(viewModel.filters.first()?.maxPrice == maxPrice.toDouble())
        }
    }

    @Test
    fun seekAreaUpdatesFilters() {
        // Given
        val filters = UIFilters(
            minPrice = 10.0,
            maxPrice = 100.0,
            minArea = 10.0,
            maxArea = 1000.0,
            minRooms = 1,
            maxRooms = 21,
            minBedrooms = 0,
            maxBedrooms = 10,
        )
        viewModel.getFilters(filters)
        val minArea = 50.0f
        val maxArea = 100.0f

        // When
        viewModel.seekArea(minArea, maxArea)

        // Then
        testScope.launch {
            assert(viewModel.filters.first()?.minArea == minArea.toDouble())
            assert(viewModel.filters.first()?.maxArea == maxArea.toDouble())
        }
    }

    @Test
    fun seekRoomsUpdatesFilters() {
        // Given
        val filters = UIFilters(
            minPrice = 10.0,
            maxPrice = 100.0,
            minArea = 10.0,
            maxArea = 1000.0,
            minRooms = 1,
            maxRooms = 21,
            minBedrooms = 0,
            maxBedrooms = 10,
        )
        viewModel.getFilters(filters)
        val minRooms = 2.0f
        val maxRooms = 3.0f

        // When
        viewModel.seekRooms(minRooms, maxRooms)

        // Then
        testScope.launch {
            assert(viewModel.filters.first()?.minRooms == minRooms.toInt())
            assert(viewModel.filters.first()?.maxRooms == maxRooms.toInt())
        }
    }

    @Test
    fun seekBedroomsUpdatesFilters() {
        // Given
        val filters = UIFilters(
            minPrice = 10.0,
            maxPrice = 100.0,
            minArea = 10.0,
            maxArea = 1000.0,
            minRooms = 1,
            maxRooms = 21,
            minBedrooms = 0,
            maxBedrooms = 10,
        )
        viewModel.getFilters(filters)
        val minBedrooms = 1.0f
        val maxBedrooms = 2.0f

        // When
        viewModel.seekBedrooms(minBedrooms, maxBedrooms)

        // Then
        testScope.launch {
            assert(viewModel.filters.first()?.minBedrooms == minBedrooms.toInt())
            assert(viewModel.filters.first()?.maxBedrooms == maxBedrooms.toInt())
        }
    }

    @Test
    fun getFiltersUpdatesFilterRangesAndFilters() {
        // Given
        val filterRanges = UIFilters(
            minPrice = 10.0,
            maxPrice = 100.0,
            minArea = 10.0,
            maxArea = 1000.0,
            minRooms = 1,
            maxRooms = 21,
            minBedrooms = 0,
            maxBedrooms = 10,
        )


        testScope.launch {
            whenever(repository.getFiltersRanges()).thenReturn(filterRanges)

            // When
            viewModel.getFilters(null)

            // Then
            assert(viewModel.filterRanges.first() == filterRanges)
            assert(viewModel.filters.first() == filterRanges)
        }
    }
}