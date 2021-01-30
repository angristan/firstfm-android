package fr.esgi.firstfm.managers

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
                return
            }

            val children = dir.list()
            children?.forEach { child: String -> File(dir, child).delete() }
            dir.delete()
        }
    }

}