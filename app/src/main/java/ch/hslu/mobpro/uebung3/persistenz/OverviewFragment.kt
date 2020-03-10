package ch.hslu.mobpro.uebung3.persistenz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_overview.*

/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment() {


    private val SHARED_PREFERENCES_OVERVIEW: String = "CounterPreference"
    private val COUNTER_KEY = "counterKey"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onResume() {
        setupCounter()

        super.onResume()
    }

    private fun setupCounter(){
        val preferences =
        requireActivity().getSharedPreferences(SHARED_PREFERENCES_OVERVIEW,
        Context.MODE_PRIVATE)

        var preferenceCounter = preferences.getInt(COUNTER_KEY, 0)

        val editor = preferences.edit()
        editor.putInt(COUNTER_KEY, ++preferenceCounter)
        editor.apply()

        counterViewElement.text = "MainActivity.onResume wurde seit der Installation dieser App $preferenceCounter mal aufgerufen."
    }
}
