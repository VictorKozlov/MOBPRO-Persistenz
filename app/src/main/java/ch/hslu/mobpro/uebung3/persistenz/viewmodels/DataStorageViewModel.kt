package ch.hslu.mobpro.uebung3.persistenz.viewmodels

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class DataStorageViewModel : ViewModel() {
    private lateinit var file: File
    private lateinit var act: Activity
    private val PER_WRITE_EXT = 1
    private val PER_READ_EXT = 2
    private lateinit var textToSave: String
    private val result: MutableLiveData<String> = MutableLiveData()

    fun getResult(): MutableLiveData<String> {
        return result
    }

    fun setActivity (activity: Activity) {
        act = activity
    }

    fun saveText(text: String, extChecked: Boolean) {
        textToSave = text
        try{
            if (extChecked) { // Save file external on SD (see attr "file")
                file = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/file.txt")

                if (!file.exists()){
                    file.createNewFile()
                }

                if (hasPermission(PER_WRITE_EXT)) {
                    file.writeText(textToSave)
                    result.value = "Speichern extern OK"
                }
                else requestPermissions(PER_WRITE_EXT)
            }
            else { //save internal (APP-space)
                file = File(act.filesDir, "outputfile.txt")
                file.writeText(textToSave)
                result.value = "Speichern intern OK"
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun loadText(extChecked: Boolean) {
        try{
            if (extChecked) { //read external
                file = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/file.txt")
                if (hasPermission(PER_READ_EXT)) {
                    if (file.exists()) result.value = file.readText() //Datei muss existieren
                    else result.value = "Keine Datei zum Laden von SD-Karte"
                }
                else requestPermissions(PER_READ_EXT)
            }
            else { //read internal
                file = File(act.filesDir, "outputfile.txt")
                if (file.exists()) result.value = file.readText() //Datei muss existieren
                else result.value = "Keine Datei im internen Verzeichnis"
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun hasPermission(permission: Int): Boolean {
        when (permission) {
            PER_WRITE_EXT -> return (act.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) //Check permission state and return (true if granted, false if not
            PER_READ_EXT -> return (act.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) //Check permission state and return (true if granted, false if not
        }
        return false
    }

    private fun requestPermissions(permission: Int) {
        act.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), permission)
        // wait for handleResult call in MainActivity
    }

    fun handleResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            PER_WRITE_EXT -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) { // permission was granted
                    file.writeText(textToSave)
                    result.value = "Speichern  OK"
                } else { // permission denied
                    result.value = "FEHLER: Keine Berechtigung"
                }
            }
            PER_READ_EXT -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) { // permission was granted
                    result.value = file.readText()
                } else { // permission denied
                    result.value = "FEHLER: Keine Berechtigung"
                }
            }
        }
    }
}