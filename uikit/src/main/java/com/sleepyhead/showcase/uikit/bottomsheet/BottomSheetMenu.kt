package com.sleepyhead.showcase.uikit.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.R
import com.sleepyhead.showcase.uikit.providers.ColorBorderColor
import com.sleepyhead.showcase.uikit.providers.ColorSurfacePrimary
import com.sleepyhead.showcase.uikit.providers.ColorTextBody
import com.sleepyhead.showcase.uikit.providers.ColorTextHeading
import com.sleepyhead.showcase.uikit.providers.MenuLabel
import com.sleepyhead.showcase.uikit.providers.Regular16
import com.sleepyhead.showcase.uikit.utils.bottomLeftRightBorders
import com.sleepyhead.showcase.uikit.utils.fullBorder
import com.sleepyhead.showcase.uikit.utils.topLeftRightBorders

data class BottomSheetMenu(
    val id: Int,
    val label: String,
    val value: String?
)

data class BottomSheetMenuSection(
    val id: Int,
    val title: String? = null,
    val menus: List<BottomSheetMenu>
)

@Composable
fun BottomSheetMenu(
    modifier: Modifier = Modifier,
    menus: List<BottomSheetMenuSection>,
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(modifier = modifier, contentPadding = contentPadding) {
        menus.forEachIndexed { index, section ->

            if (!section.title.isNullOrBlank()) {
                item(key = section.id) {
                    val topPadding = if (index == 0) 0.dp else 24.dp
                    Box(modifier = Modifier.padding(bottom = 16.dp, top = topPadding)) {
                        BasicText(text = section.title, style = MenuLabel.copy(color = ColorTextBody))
                    }
                }
            }

            itemsIndexed(
                items = section.menus,
                key = { _, item -> item.id }
            ) { index, item ->
                BottomSheetMenuItem(
                    menu = item,
                    isFirst = index == 0,
                    isLast = index == section.menus.lastIndex
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBottomSheetMenu() {
    val menus = remember {
        listOf(
            BottomSheetMenu(
                id = 1,
                label = "Create",
                value = "AI"
            ),
            BottomSheetMenu(
                id = 2,
                label = "Edit",
                value = null
            )
        )
    }
    Box(modifier = Modifier
        .background(ColorSurfacePrimary)
        .padding(10.dp)) {
        BottomSheetMenu(menus = listOf(BottomSheetMenuSection(id = 0, title = "Hello", menus = menus)))
    }
}


@Composable
private fun BottomSheetMenuItem(
    menu: BottomSheetMenu,
    isFirst: Boolean,
    isLast: Boolean
) {
    val shape = if (isFirst && isLast) {
        RoundedCornerShape(16.dp)
    } else if (isFirst) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    } else if (isLast) {
        RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    } else {
        null
    }

    val border = when {
        isFirst && !isLast -> {
            Modifier.topLeftRightBorders(color = ColorBorderColor, width = 1.dp, radius = 16.dp)
        }

        isLast && !isFirst -> {
            Modifier.bottomLeftRightBorders(color = ColorBorderColor, width = 1.dp, radius = 16.dp)
        }

        isFirst && isLast -> {
            Modifier.fullBorder(color = ColorBorderColor, width = 1.dp, radius = 16.dp)
        }

        else -> {
            Modifier.topLeftRightBorders(color = ColorBorderColor, width = 1.dp, radius = 0.dp)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .then(if (shape != null) Modifier.clip(shape) else Modifier)
            .background(Color.White)
            .then(border)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(text = menu.label, style = Regular16.copy(color = ColorTextHeading))
        Spacer(modifier = Modifier.weight(1f))
        if (!menu.value.isNullOrBlank()) {
            BasicText(
                text = menu.value,
                style = Regular16.copy(color = ColorTextBody)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(R.drawable.ic_bottom_sheet_menu),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewBottomSheetMenuItem() {
    Box(
        modifier = Modifier
            .background(ColorSurfacePrimary)
            .padding(24.dp)
    ) {
        BottomSheetMenuItem(
            menu = BottomSheetMenu(
                id = 1,
                label = "Created by",
                value = "Linda Walker"
            ),
            isFirst = false,
            isLast = false
        )
    }
}