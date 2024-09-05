package com.example.mypokedex.util

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.ui.graphics.Color as ComposeColor

suspend fun getDominantColorFromImage(context: Context, url: String, imageLoader: ImageLoader): ComposeColor {
    val request = ImageRequest.Builder(context)
        .data(url)
        .size(Size.ORIGINAL)
        .build()

    return withContext(Dispatchers.IO) {
        val drawable = imageLoader.execute(request).drawable
        val bitmap = drawable?.toBitmap() ?: return@withContext ComposeColor.Transparent

        val convertedBitmap = if (bitmap.config == Bitmap.Config.HARDWARE) {
            bitmap.copy(Bitmap.Config.ARGB_8888, true)
        } else {
            bitmap
        }

        val dominantColorInt = extractDominantColor(convertedBitmap)
        ComposeColor(dominantColorInt)
    }
}

fun extractDominantColor(bitmap: Bitmap): Int {
    val palette = Palette.from(bitmap).generate()
    return palette.getDominantColor(android.graphics.Color.TRANSPARENT)
}
