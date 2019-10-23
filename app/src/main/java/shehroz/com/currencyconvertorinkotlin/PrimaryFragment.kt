package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_primary.*
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class PrimaryFragment : Fragment(), AdapterView.OnItemSelectedListener,Animation.AnimationListener {
    var appContext:AppContext? = null
    var asyncTask : AsyncTask<URL, Void, String>? = null
    lateinit var blinkAnim : Animation
    lateinit var keyboard : InputMethodManager
    lateinit var currencyAmount : EditText
    var simpleCursorAdapter : SpinnerCustomAdapter? = null
    var treeMap: TreeMap<String, Country> = TreeMap()
    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(simpleCursorAdapter!=null){
            val key =  simpleCursorAdapter!!.getItem(p2) as String
            currency.text = key
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_primary, container, false) as View

        currencyAmount = view.findViewById<EditText>(R.id.amount)
        val countrySpinner = view.findViewById<Spinner>(R.id.countrySpinner)
        keyboard = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        simpleCursorAdapter = SpinnerCustomAdapter(context!!.applicationContext,getCountriesHashMap())
        countrySpinner.adapter = simpleCursorAdapter
        countrySpinner.onItemSelectedListener = this
        blinkAnim = AnimationUtils.loadAnimation(context,R.anim.blink)
        blinkAnim.setAnimationListener(this)
        view.findViewById<Button>(R.id.convert).setOnClickListener(View.OnClickListener {
            if (appContext!!.checkNetworkConnectivity()) {
                if (currencyAmount.text.isEmpty()) {
                    // Notice that I didn't need to call findViewById(), thanks to Synthetic Binding!
                    error.visibility = View.VISIBLE
                    currencyAmountViewGroup.setBackgroundResource(R.drawable.error_bg)
                    currencyAmount.clearFocus()
                    currencyAmountViewGroup.startAnimation(blinkAnim)
                }
                else {
                    if(isAsyncTaskRunning()){
                        asyncTask!!.cancel(true)
                        asyncTask = null
                    }
                    currencyAmount.clearFocus()
                    keyboard.hideSoftInputFromWindow(it.windowToken, 0)
                    asyncTask = RunInBackground()
                    asyncTask!!.execute(formURL(currency.text.toString()))
                }
            }
        })
        currencyAmount.addTextChangedListener(textWatcher)
        return view
    }

    override fun onAnimationRepeat(p0: Animation?) {}

    override fun onAnimationEnd(p0: Animation?) {
        if(p0==blinkAnim){
            currencyAmountViewGroup.clearAnimation()
            error.visibility = View.GONE
            currencyAmountViewGroup.setBackgroundResource(0)
            currencyAmount.requestFocus()
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
        return treeMap.toList().sortedBy {(_,country)-> country.countryName}.toMap()
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

    private fun isAsyncTaskRunning():Boolean{
        if(asyncTask != null && (asyncTask!!.status == AsyncTask.Status.RUNNING)){
            return true
        }
        return false
    }

    override fun onDetach() {
        super.onDetach()
        if(appContext!=null)appContext=null
    }

    inner class RunInBackground : AsyncTask<URL, Void, String>() {
        override fun doInBackground(vararg params: URL?): String {
            return getResponseFromNetwork(params[0]!!)
        }
        override fun onPostExecute(result: String?) {
            if (!TextUtils.isEmpty(result)) {
                val model = Gson().fromJson(result,Model::class.java)
                appContext?.notifySecondaryFragment(model,currencyAmount.text.toString())
            }
        }
    }
}
