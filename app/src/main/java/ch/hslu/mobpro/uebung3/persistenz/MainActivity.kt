package ch.hslu.mobpro.uebung3.persistenz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import ch.hslu.mobpro.uebung3.persistenz.viewmodels.DataStorageViewModel

class MainActivity : AppCompatActivity() {
    private val dataStorageViewModel: DataStorageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.addHereFragmentView, OverviewFragment())
            .commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        dataStorageViewModel.handleResult(requestCode, grantResults)
    }
}
