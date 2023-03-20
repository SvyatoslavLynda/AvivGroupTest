package com.svdroid.avivgrouptest.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.svdroid.avivgrouptest.data.ui.UIPropertyModel
import com.svdroid.avivgrouptest.ui.property.PropertiesActions

@Composable
fun PropertiesList(items: List<UIPropertyModel>, actions: PropertiesActions, onFavoriteClick: (property: UIPropertyModel) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(
            count = items.size,
            itemContent = { index ->
                val item = items[index]

                PropertyItem(item, actions, onFavoriteClick)
            }
        )
    }
}