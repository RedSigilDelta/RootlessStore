package com.baidaidai.rootless_store.ui.components.pluginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.baidaidai.rootless_store.ui.R

@Composable
fun ColorfulBadge(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    contentColor: Color,
    content: @Composable (()-> Unit),
    leadingContent: (@Composable (()-> Unit))? = null,
    badgeMinHeight: Dp = 24.dp,
){
    val verticalPadding = 4.dp
    val leadingContentMaxHeight = badgeMinHeight - verticalPadding * 2

    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        shape = CircleShape,
        modifier = modifier
            .defaultMinSize(minHeight = badgeMinHeight)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = verticalPadding)
        ) {
            if (leadingContent != null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(leadingContentMaxHeight)
                        .aspectRatio(1f)
                ) {
                    leadingContent()
                }
            }
            content()
        }
    }
}

@PreviewLightDark
@Composable
private fun _preview_() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        ColorfulBadge(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            leadingContent = {
                Icon(
                    painter = painterResource(R.drawable.material_symbols_check),
                    contentDescription = null
                )
            },
            content = {
                Text("Signed")
            }
        )
        ColorfulBadge(
            backgroundColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
            content = {
                Text("Unsigned")
            }
        )
    }
}
