package com.svdroid.avivgrouptest.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.svdroid.avivgrouptest.R

@Composable
fun ErrorState(modifier: Modifier = Modifier, text: String = stringResource(id = R.string.message_error)) {
    Text(
        text = stringResource(R.string.formatted_message_error, text),
        modifier = modifier.padding(16.dp),
        textAlign = TextAlign.Center
    )
}