package com.svdroid.avivgrouptest

import com.svdroid.avivgrouptest.data.utils.DataState
import org.junit.Assert.assertEquals
import org.junit.Test

class DataStateTest {
    @Test
    fun loadingState() {
        val state = DataState.Loading<Int>()
        assertEquals(state.javaClass, DataState.Loading::class.java)
    }

    @Test
    fun dataStateWithData() {
        val data = 42
        val state = DataState.Data(data)
        assertEquals(state.javaClass, DataState.Data::class.java)
        assertEquals(state.data, data)
    }

    @Test
    fun dataStateWithNullData() {
        val state = DataState.Data<Int>(null)
        assertEquals(state.javaClass, DataState.Data::class.java)
        assertEquals(state.data, null)
    }

    @Test
    fun errorState() {
        val message = "Something went wrong"
        val state = DataState.Error<Int>(message)
        assertEquals(state.javaClass, DataState.Error::class.java)
        assertEquals(state.message, message)
    }
}