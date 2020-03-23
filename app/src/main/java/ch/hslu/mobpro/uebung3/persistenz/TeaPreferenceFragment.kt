package ch.hslu.mobpro.uebung3.persistenz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.lifecycle.ViewModelProvider
import ch.hslu.mobpro.uebung3.persistenz.viewmodels.SharedPreferencesViewModel

/**
 * A simple [Fragment] subclass.
 */
class TeaPreferenceFragment : PreferenceFragmentCompat() {

    companion object {
        fun newInstance(): TeaPreferenceFragment {
            return TeaPreferenceFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
