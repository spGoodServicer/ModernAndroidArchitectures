package com.nereus.craftbeer.util

import com.nereus.craftbeer.realm.RealmApplication
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TLogger {
    var _logfilename : String? = null

    /**
     * Init
     *
     * @param logfilename
     */
    fun Init(logfilename : String){
        _logfilename = logfilename
    }

    /**
     * Writeln
     *
     * @param logString
     */
    fun writeln(logString: String) {
        try {
            /*
            if (!BuildConfig.DEBUG) {
                return
            }
            */

            var logPath = RealmApplication.instance.applicationContext.getExternalFilesDir("Log")
            if(_logfilename==null)
            {
                _logfilename = "applog.txt"
            }
            val file = File(logPath, _logfilename)
            if (!file.exists())
            {
                file.createNewFile();
                file.writeText("\uFEFF", Charsets.UTF_8)
            }
            var fw = FileWriter(file, true);
            val formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss")
            val dt = LocalDateTime.now()
            fw.append(dt.format(formatter).toString() + ":")
            fw.append(logString)
            fw.append("\n")
            fw.close()

            print(logString)
        } catch (e: Exception) {
            println(e)
            println("Writing CSV error!")
        }
    }
}