package com.sleepyhead.showcase.uikit.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.topLeftRightBorders(
    color: Color,
    width: Dp,
    radius: Dp = 0.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val strokePx = width.toPx()
        val halfStroke = strokePx / 2
        val radiusPx = radius.toPx()
        // Inset path so Stroke is fully inside bounds (same visible thickness as sides)
        val pathRadius = (radiusPx - halfStroke).coerceAtLeast(0f)

        clipRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = radiusPx + strokePx
        ) {
            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            left = halfStroke,
                            top = halfStroke,
                            right = size.width - halfStroke,
                            bottom = size.height - halfStroke
                        ),
                        cornerRadius = CornerRadius(pathRadius, pathRadius)
                    )
                )
            }
            drawPath(
                path = path,
                color = color,
                style = Stroke(width = strokePx)
            )
        }

        // Left / right start below top corner arcs to avoid double-draw
        val xLeft = halfStroke
        drawLine(
            color = color,
            start = Offset(xLeft, radiusPx),
            end = Offset(xLeft, size.height),
            strokeWidth = strokePx
        )

        val xRight = size.width - halfStroke
        drawLine(
            color = color,
            start = Offset(xRight, radiusPx),
            end = Offset(xRight, size.height),
            strokeWidth = strokePx
        )
    }
)

fun Modifier.bottomLeftRightBorders(
    color: Color,
    width: Dp,
    radius: Dp = 0.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val strokePx = width.toPx()
        val halfStroke = strokePx / 2
        val radiusPx = radius.toPx()
        // Inset path so Stroke is fully inside bounds (same visible thickness as sides)
        val pathRadius = (radiusPx - halfStroke).coerceAtLeast(0f)

        clipRect(
            left = 0f,
            top = size.height - radiusPx - strokePx,
            right = size.width,
            bottom = size.height
        ) {
            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            left = halfStroke,
                            top = halfStroke,
                            right = size.width - halfStroke,
                            bottom = size.height - halfStroke
                        ),
                        cornerRadius = CornerRadius(pathRadius, pathRadius)
                    )
                )
            }
            drawPath(
                path = path,
                color = color,
                style = Stroke(width = strokePx)
            )
        }

        // Flat top border (no radius)
        drawLine(
            color = color,
            start = Offset(halfStroke, halfStroke),
            end = Offset(size.width - halfStroke, halfStroke),
            strokeWidth = strokePx
        )

        // Left / right stop above bottom corner arcs to avoid double-draw
        val xLeft = halfStroke
        drawLine(
            color = color,
            start = Offset(xLeft, 0f),
            end = Offset(xLeft, size.height - radiusPx),
            strokeWidth = strokePx
        )

        val xRight = size.width - halfStroke
        drawLine(
            color = color,
            start = Offset(xRight, 0f),
            end = Offset(xRight, size.height - radiusPx),
            strokeWidth = strokePx
        )
    }
)

fun Modifier.fullBorder(
    color: Color,
    width: Dp,
    radius: Dp = 0.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val strokePx = width.toPx()
        val halfStroke = strokePx / 2
        val radiusPx = radius.toPx()
        val pathRadius = (radiusPx - halfStroke).coerceAtLeast(0f)

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        left = halfStroke,
                        top = halfStroke,
                        right = size.width - halfStroke,
                        bottom = size.height - halfStroke
                    ),
                    cornerRadius = CornerRadius(pathRadius, pathRadius)
                )
            )
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(width = strokePx)
        )
    }
)
