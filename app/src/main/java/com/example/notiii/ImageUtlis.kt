package com.example.notiii

// ImageUtils.kt
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import java.io.FileNotFoundException
import java.io.InputStream

object ImageUtils {
    fun getDrawableFromUri(context: Context, uri: Uri): Drawable? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (inputStream != null) {
                Drawable.createFromStream(inputStream, null)
            } else {
                // Handle the case where the input stream is null (e.g., invalid URI)
                null
            }
        } catch (e: FileNotFoundException) {
            // Handle the file not found error
            null
        }
    }
}
