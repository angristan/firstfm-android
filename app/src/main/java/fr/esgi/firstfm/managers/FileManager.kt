package fr.esgi.firstfm.managers

import android.util.Log
import java.io.File

class FileManager {

    companion object Directory {

        fun create(dir: File) {
            if (!dir.exists()) {
                dir.mkdirs()
            }
        }

        fun delete(dir: File) {
            if (!dir.isDirectory) {
                Log.d("not dir:", dir.absolutePath)
                return
            }

            val children = dir.list()
            children?.forEach { child: String -> File(dir, child).delete() }
            dir.delete()
        }
    }

}