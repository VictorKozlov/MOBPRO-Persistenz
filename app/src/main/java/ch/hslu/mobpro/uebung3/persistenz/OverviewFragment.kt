package ch.hslu.mobpro.uebung3.persistenz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import ch.hslu.mobpro.uebung3.persistenz.viewmodels.DataStorageViewModel
import kotlinx.android.synthetic.main.fragment_overview.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
/*import androidx.preference.PreferenceManage*/

/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment() {


    private val SHARED_PREFERENCES_OVERVIEW: String = "CounterPreference"
    private val COUNTER_KEY = "counterKey"
    private val dataStorageViewModel: DataStorageViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            var view = inflater.inflate(R.layout.fragment_overview, container, false)

            var btnTeaPreference = view?.findViewById<Button>(R.id.setTeaPreferenceButton)
            var btnDefaultTeaPreference = view?.findViewById<Button>(R.id.setDefaultTeaPreferenceButton)
            var btnLoad = view?.findViewById<Button>(R.id.btn_load)
            var btnSave = view?.findViewById<Button>(R.id.btn_save)
            btnTeaPreference?.setOnClickListener{
                this.parentFragmentManager.beginTransaction()?.replace(R.id.addHereFragmentView, TeaPreferenceFragment()).addToBackStack("overview")?.commit()
            }
            btnDefaultTeaPreference?.setOnClickListener{
                setDefaults()
                this.parentFragmentManager.beginTransaction()?.replace(R.id.addHereFragmentView, TeaPreferenceFragment()).addToBackStack("overview")?.commit()
            }
            // Inflate the layout for this fragment
            //SAVE-EXTERNAL
            dataStorageViewModel.setActivity(requireActivity()) //z.B. für internen Speicherpfad und zum prüfen der Berechtigungen benötigt
            dataStorageViewModel.getResult().observe(viewLifecycleOwner, Observer { newVal: String -> //Anzuzeigenden String für EditText
                txt_status.setText(newVal)
            })
            btnSave?.setOnClickListener {
                dataStorageViewModel.saveText(txt_editText.text.toString(), cbt_externDataStorage.isChecked)
            }
            btnLoad?.setOnClickListener {
                dataStorageViewModel.loadText(cbt_externDataStorage.isChecked)
            }
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
