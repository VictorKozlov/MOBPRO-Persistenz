package ch.hslu.mobpro.uebung3.persistenz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat;

/**
 * A simple [Fragment] subclass.
 */
class TeaPreferenceFragment : PreferenceFragmentCompat() {

    companion object {
        fun newInstance(): TeaPreferenceFragment {
            return TeaPreferenceFragment()
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
