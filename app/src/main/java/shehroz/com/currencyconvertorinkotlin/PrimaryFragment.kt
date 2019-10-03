package shehroz.com.currencyconvertorinkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView

class PrimaryFragment(appContext: AppContext): BaseFragment(appContext),AdapterView.OnItemSelectedListener {
    var appContext:AppContext = appContext
    lateinit var currencyTextView : TextView
    var simpleCursorAdapter : SpinnerCustomAdapter? = null
    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(simpleCursorAdapter!=null){
            val key =  simpleCursorAdapter!!.getItem(p2) as String
            currencyTextView.text = key
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_primary, container, false) as View
        currencyTextView = view.findViewById<TextView>(R.id.currency)
        val countrySpinner = view.findViewById<Spinner>(R.id.countrySpinner)
        simpleCursorAdapter = SpinnerCustomAdapter(appContext.getContext(),appContext.getCountriesHashMap())
        countrySpinner.adapter = simpleCursorAdapter
        countrySpinner.onItemSelectedListener = this
        return view
    }
}
