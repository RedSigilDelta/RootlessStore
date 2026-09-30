package com.baidaidai.rootless_store.ui.components.pluginScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baidaidai.rootless_store.ui.R
import com.baidaidai.rootless_store.ui.components.common.VerticalSpacer
import com.baidaidai.rootless_store.ui.theme.ColorFactory
import com.baidaidai.rootless_store.ui.theme.SuccessColorSet
import com.baidaidai.rootless_store.ui.theme.WarningColorSet

@Composable
fun PluginBottomSheetContent(
    modifier: Modifier = Modifier,
    pluginMeta: String = "",
    onDismiss: ()-> Unit = {},
    onConfirm: ()-> Unit = {}
){

    val a: Int = 4
    val badgeContainerColor = when(a){
        1 -> {
            // Safe 
            ColorFactory(SuccessColorSet).color
        }
        2 -> {
            // Wrning
            ColorFactory(WarningColorSet).color
        }
        else -> {
            // Error
            MaterialTheme.colorScheme.error
        }

    }
    val badgeColor = when(a){
        1 -> {
            // Safe
            ColorFactory(SuccessColorSet).onColor
        }
        2 -> {
            // Wrning
            ColorFactory(WarningColorSet).onColor
        }
        else -> {
            // Error
            MaterialTheme.colorScheme.onError
        }

    }
    val badge = when(a){
        1 -> {
            // Safe
            R.drawable.material_symbols_shield_success
        }
        2 -> {
            // Wrning
            R.drawable.material_symbols_shield_question
        }
        else -> {
            // Error
            R.drawable.material_symbols_shield_bad
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(16.dp)
                        .clip(CircleShape)
                        .background(
                            color = badgeContainerColor
                        )
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Icon(
                        painter = painterResource(badge),
                        contentDescription = null,
                        tint = badgeColor,
                        modifier = Modifier
                            .size(40.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(2f)
                ) {
                    Text(
                        text = pluginMeta.ifBlank { "Demo Plugin" },
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600
                    )
                    VerticalSpacer(10.dp)

                    Text(
                        text = "Author: Normal",
                        style = MaterialTheme.typography.bodySmall
                    )
                    VerticalSpacer(4.dp)
                    Text(
                        text = "Security Level: Normal",
                        style = MaterialTheme.typography.bodySmall,
                    )


                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "This plugin has not been signed by SSL and cannot be confirmed to be secure. Please carefully consider before making your choice.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight(440)

                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Dismiss")
                }
                Button(
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Confirm")
                }
            }
        }
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(15.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-8).dp, y = 8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.material_symbols_info_i),
                contentDescription = "Why about this page",
                modifier = Modifier.size(10.dp)
            )
        }
    }

}

@PreviewLightDark
@Composable
private fun _preview_() {
    PluginBottomSheetContent(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
    )
}
