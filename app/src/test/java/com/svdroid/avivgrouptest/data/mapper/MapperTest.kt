package com.svdroid.avivgrouptest.data.mapper

import com.svdroid.avivgrouptest.data.api.PropertyResponse
import com.svdroid.avivgrouptest.data.db.PropertiesEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    @Test
    fun testToUI() {
        val entity = PropertiesEntity(
            id = 1,
            bedrooms = 2,
            rooms = 3,
            offerType = 1,
            area = 100.0,
            price = 200000.0,
            url = "http://example.com",
            city = "City",
            professional = "Professional",
            propertyType = "Type",
            isFavorite = false
        )

        val ui = entity.toUI()

        assertEquals(entity.id, ui.id)
        assertEquals(entity.bedrooms, ui.bedrooms)
        assertEquals(entity.rooms, ui.rooms)
        assertEquals(entity.offerType, ui.offerType)
        assertEquals(entity.area, ui.area)
        assertEquals(entity.price, ui.price)
        assertEquals(entity.url, ui.url)
        assertEquals(entity.city, ui.city)
        assertEquals(entity.professional, ui.professional)
        assertEquals(entity.propertyType, ui.propertyType)
        assertEquals(entity.isFavorite, ui.isFavorite)
    }

    @Test
    fun testToDB() {
        val response = PropertyResponse(
            id = 1,
            bedrooms = 2,
            rooms = 3,
            offerType = 1,
            area = 100.0,
            price = 200000.0,
            url = "http://example.com",
            city = "City",
            professional = "Professional",
            propertyType = "Type"
        )

        val entity = response.toDB(isFavorite = true)

        assertEquals(response.id, entity.id)
        assertEquals(response.bedrooms, entity.bedrooms)
        assertEquals(response.rooms, entity.rooms)
        assertEquals(response.offerType, entity.offerType)
        assertEquals(response.area, entity.area)
        assertEquals(response.price, entity.price)
        assertEquals(response.url, entity.url)
        assertEquals(response.city, entity.city)
        assertEquals(response.professional, entity.professional)
        assertEquals(response.propertyType, entity.propertyType)
        assertEquals(true, entity.isFavorite)
    }

    @Test
    fun testToUIFilters() {
        val properties = listOf(
            PropertiesEntity(
                id = 1,
                bedrooms = 2,
                rooms = 3,
                offerType = 0,
                area = 70.0,
                price = 100000.0,
                url = "",
                city = "Berlin",
                professional = "John Doe",
                propertyType = "Apartment",
                isFavorite = false
            ),
            PropertiesEntity(
                id = 2,
                bedrooms = 3,
                rooms = 4,
                offerType = 1,
                area = 80.0,
                price = 150000.0,
                url = "",
                city = "New York",
                professional = "Jane Doe",
                propertyType = "House",
                isFavorite = false
            ),
            PropertiesEntity(
                id = 3,
                bedrooms = 4,
                rooms = 5,
                offerType = 0,
                area = 90.0,
                price = 200000.0,
                url = "",
                city = "Paris",
                professional = "Max Mustermann",
                propertyType = "Penthouse",
                isFavorite = false
            )
        )

        val uiFilters = properties.toUIFilters()

        assertEquals(100000.0, uiFilters.minPrice, 0.0)
        assertEquals(200000.0, uiFilters.maxPrice, 0.0)
        assertEquals(70.0, uiFilters.minArea, 0.0)
        assertEquals(90.0, uiFilters.maxArea, 0.0)
        assertEquals(3, uiFilters.minRooms)
        assertEquals(5, uiFilters.maxRooms)
        assertEquals(2, uiFilters.minBedrooms)
        assertEquals(4, uiFilters.maxBedrooms)
    }

    @Test
    fun testToUIFiltersWithObjectsInListWithoutSomeFields() {
        val properties = listOf(
            PropertiesEntity(
                id = 1,
                bedrooms = null,
                rooms = 3,
                offerType = 0,
                area = null,
                price = 100000.0,
                url = "",
                city = "Berlin",
                professional = "John Doe",
                propertyType = "Apartment",
                isFavorite = false
            ),
            PropertiesEntity(
                id = 2,
                bedrooms = 3,
                rooms = 4,
                offerType = 1,
                area = 80.0,
                price = null,
                url = "",
                city = "New York",
                professional = "Jane Doe",
                propertyType = "House",
                isFavorite = false
            ),
            PropertiesEntity(
                id = 3,
                bedrooms = 4,
                rooms = null,
                offerType = 0,
                area = 90.0,
                price = null,
                url = "",
                city = "Paris",
                professional = "Max Mustermann",
                propertyType = "Penthouse",
                isFavorite = false
            )
        )

        val uiFilters = properties.toUIFilters()

        assertEquals(0.0, uiFilters.minPrice, 0.0)
        assertEquals(100000.0, uiFilters.maxPrice, 0.0)
        assertEquals(0.0, uiFilters.minArea, 0.0)
        assertEquals(90.0, uiFilters.maxArea, 0.0)
        assertEquals(0, uiFilters.minRooms)
        assertEquals(4, uiFilters.maxRooms)
        assertEquals(0, uiFilters.minBedrooms)
        assertEquals(4, uiFilters.maxBedrooms)
    }
}
