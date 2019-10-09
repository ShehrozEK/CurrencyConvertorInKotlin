package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import android.graphics.PorterDuff
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class PrimaryFragment(): BaseFragment(),AdapterView.OnItemSelectedListener,Animation.AnimationListener {
    lateinit var appContext:AppContext
    lateinit var currencyTextView : TextView
    lateinit var errorTextView: TextView
    lateinit var currencyAmount : EditText
    lateinit var currencyAmountViewGroup: LinearLayout
    lateinit var blinkAnim : Animation
    var simpleCursorAdapter : SpinnerCustomAdapter? = null
    var treeMap: TreeMap<String, Country> = TreeMap()

    constructor(appContext: AppContext):this(){
        this.appContext = appContext
        BaseFragment(appContext)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

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
        errorTextView = view.findViewById<TextView>(R.id.error)
        currencyAmount = view.findViewById<EditText>(R.id.amount)
        currencyAmountViewGroup = view.findViewById<LinearLayout>(R.id.currencyAmountViewGroup)
        val countrySpinner = view.findViewById<Spinner>(R.id.countrySpinner)
        simpleCursorAdapter = SpinnerCustomAdapter(context!!.applicationContext,getCountriesHashMap())
        countrySpinner.adapter = simpleCursorAdapter
        countrySpinner.onItemSelectedListener = this
        blinkAnim = AnimationUtils.loadAnimation(context,R.anim.blink)
        blinkAnim.setAnimationListener(this)
        view.findViewById<Button>(R.id.convert).setOnClickListener(View.OnClickListener {
            if(currencyAmount.text.isEmpty()){
                errorTextView.visibility = View.VISIBLE
                currencyAmountViewGroup.setBackgroundResource(R.drawable.error_bg)
                currencyAmount.clearFocus()
                currencyAmountViewGroup.startAnimation(blinkAnim)
            }
            else{
                val asyncTask = RunInBackground()
                asyncTask.execute(formURL(currencyTextView.text.toString()))
            }
        })
        currencyAmount.addTextChangedListener(textWatcher)
        return view
    }

    override fun onAnimationRepeat(p0: Animation?) {}

    override fun onAnimationEnd(p0: Animation?) {
        if(p0==blinkAnim){
            currencyAmountViewGroup.clearAnimation()
            errorTextView.visibility = View.GONE
            currencyAmountViewGroup.setBackgroundResource(0)
            currencyAmount.requestFocus()
            val keyboard = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(currencyAmount,InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun onAnimationStart(p0: Animation?) {}

    private val textWatcher = object :TextWatcher{
        override fun afterTextChanged(p0: Editable?) {
            if(!blinkAnim.hasEnded()){
               blinkAnim.cancel()
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    private fun getCountriesHashMap(): Map<String, Country> {
        if(treeMap.size<=0) {
            val typedArray = resources.obtainTypedArray(R.array.countries)
            for (i in 0 until typedArray.length()) {
                val id  = typedArray.getResourceId(i,0)
                if(id>0){
                    val arr = resources.getStringArray(id) as Array<String>
                    val arrName = resources.getResourceEntryName(id) as String
                    val country = Country()
                    country.countryName = arr[0]
                    country.countryFlag = arr[1]
                    country.countryCurrency = arr[2]
                    treeMap[arrName] = country
                }
            }
            typedArray.recycle()
        }
        return treeMap.toList().sortedBy { (_,country)-> country.countryName}.toMap()
    }

    private fun formURL(urlParameter:String): URL {
        return URL(resources.getString(R.string.url).plus(urlParameter))
    }

    private fun getResponseFromNetwork(url: URL):String {
        val httpURLConnection = url.openConnection() as HttpURLConnection
        val inputStream : InputStream = httpURLConnection.inputStream
        val scanner = Scanner(inputStream)
        scanner.useDelimiter("\\A")
        while(scanner.hasNext()){
            return scanner.next()
        }
        return ""
    }

    inner class RunInBackground : AsyncTask<URL, Void, String>() {
        override fun doInBackground(vararg params: URL?): String {
            return getResponseFromNetwork(params[0]!!)
        }
        override fun onPostExecute(result: String?) {
            if (!TextUtils.isEmpty(result)) {
                val model = Gson().fromJson(result,Model::class.java)
                populateRecyclerView(model)
            }
        }
    }

    private fun populateRecyclerView(model: Model){

    }
}
