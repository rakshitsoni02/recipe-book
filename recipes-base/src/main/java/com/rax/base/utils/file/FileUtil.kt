package com.rax.base.utils.file

import android.os.Environment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object FileUtil {

    /**
     * Get Image File
     *
     * Default it will take Camera folder as it's directory
     *
     * @param dir File Folder in which file needs tobe created.
     * @param extension String Image file extension.
     * @return Return Empty file to store camera image.
     * @throws IOException if permission denied of failed to create new file.
     */
    fun getImageFile(dir: File? = null, extension: String? = null): File? {
        try {
            // Create an image file name
            val ext = extension ?: ".jpg"
            val imageFileName = "IMG_${getTimestamp()}$ext"

            // Create File Directory Object
            val storageDir = dir ?: getCameraDirectory()

            // Create Directory If not exist
            if (!storageDir.exists()) storageDir.mkdirs()

            // Create File Object
            val file = File(storageDir, imageFileName)

            // Create empty file
            file.createNewFile()

            return file
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }

    /**
     * Get Camera Image Directory
     *
     * @return File Camera Image Directory
     */
    private fun getCameraDirectory(): File {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        return File(dir, "Camera")
    }

    /**
     * Get Current Time in yyyyMMdd HHmmssSSS format
     *
     * 2019/01/30 10:30:20 000
     * E.g. 20190130_103020000
     */
    private fun getTimestamp(): String {
        val timeFormat = "yyyyMMdd_HHmmssSSS"
        return SimpleDateFormat(timeFormat, Locale.getDefault()).format(Date())
    }


}