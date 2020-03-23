package ch.hslu.mobpro.uebung3.persistenz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.fragment_overview.*
/*import androidx.preference.PreferenceManage*/

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
        var view = inflater.inflate(R.layout.fragment_overview, container, false)
        var btnTeaPreference = view?.findViewById<Button>(R.id.setTeaPreferenceButton)
        btnTeaPreference?.setOnClickListener{
            this.parentFragmentManager.beginTransaction()?.replace(R.id.addHereFragmentView, TeaPreferenceFragment()).addToBackStack("overview")?.commit()
        }

        var btnDefaultTeaPreference = view?.findViewById<Button>(R.id.setDefaultTeaPreferenceButton)
        btnDefaultTeaPreference?.setOnClickListener{
            setDefaults()
            this.parentFragmentManager.beginTransaction()?.replace(R.id.addHereFragmentView, TeaPreferenceFragment()).addToBackStack("overview")?.commit()
        }
            // Inflate the layout for this fragment
            return view;
        }

    private fun setDefaults() {
        var prefs = PreferenceManager.getDefaultSharedPreferences(context)
        var editor = prefs.edit()
        editor.putBoolean("teaWithSugar", false)
        editor.putString("teaSweetener", "natural")
        editor.putString("teaPreferred", "defaultTea")
        editor.commit()
    }

    override fun onResume() {
        setupCounter()
        setupPreferences()

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

    private fun setupPreferences(){
        var prefs = PreferenceManager.getDefaultSharedPreferences(context)
        var isSweeten = prefs.getBoolean("teaWithSugar", false)
        var sweetenString : String = if (isSweeten) "mit ${prefs.getString("teaSweetener", null) } gesüsst" else ""
        teaPrefereceViewElement.text = "Ich trinke am liebsten  ${prefs.getString("teaPreferred", null)},   ${sweetenString}"
    }

    public fun setTeaPreference(view : View){
        PreferenceManager.getDefaultSharedPreferences(context)
    }
}
