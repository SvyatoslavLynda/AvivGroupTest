package com.svdroid.avivgrouptest.data.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import com.svdroid.avivgrouptest.R
import com.svdroid.avivgrouptest.data.api.PropertyResponse
import com.svdroid.avivgrouptest.data.db.PropertiesEntity
import com.svdroid.avivgrouptest.data.ui.UIFilters
import com.svdroid.avivgrouptest.data.ui.UIInfoRecord
import com.svdroid.avivgrouptest.data.ui.UIPropertyModel
import com.svdroid.avivgrouptest.utils.extension.convertAreaToString
import com.svdroid.avivgrouptest.utils.extension.convertPriceToString

fun PropertiesEntity.toUI(): UIPropertyModel = UIPropertyModel(
    id = this.id,
    bedrooms = this.bedrooms,
    rooms = this.rooms,
    offerType = this.offerType ?: -1,
    area = this.area,
    price = this.price,
    url = this.url,
    city = this.city ?: "N/A",
    professional = this.professional ?: "N/A",
    propertyType = this.propertyType ?: "N/A",
    isFavorite = this.isFavorite,
)

fun PropertyResponse.toDB(isFavorite: Boolean): PropertiesEntity = PropertiesEntity(
    id = id,
    bedrooms = bedrooms,
    rooms = rooms,
    offerType = offerType,
    area = area,
    price = price,
    url = url,
    city = city,
    professional = professional,
    propertyType = propertyType,
    isFavorite = isFavorite,
)

fun List<PropertiesEntity>.toUIFilters(): UIFilters = UIFilters(
    minPrice = minOf { it.price ?: 0.0 },
    maxPrice = maxOf { it.price ?: 0.0 },
    minArea = minOf { it.area ?: 0.0 },
    maxArea = maxOf { it.area ?: 0.0 },
    minRooms = minOf { it.rooms ?: 0 },
    maxRooms = maxOf { it.rooms ?: 0 },
    minBedrooms = minOf { it.bedrooms ?: 0 },
    maxBedrooms = maxOf { it.bedrooms ?: 0 },
)

@Composable
fun UIPropertyModel.toInfoRecords(): List<UIInfoRecord> {
    val records = mutableListOf<UIInfoRecord>()

    price?.apply { records.add(UIInfoRecord(R.drawable.price_24, convertPriceToString())) }
    area?.apply { records.add(UIInfoRecord(R.drawable.area_24, convertAreaToString())) }
    rooms?.apply { records.add(UIInfoRecord(R.drawable.room_24, pluralStringResource(id = R.plurals.rooms_plurals, this, this))) }
    bedrooms?.apply { records.add(UIInfoRecord(R.drawable.bedroom_24, pluralStringResource(id = R.plurals.bedrooms_plurals, this, this))) }

    return records
}