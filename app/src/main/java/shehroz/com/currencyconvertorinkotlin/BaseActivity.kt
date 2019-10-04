package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.HashMap


open class BaseActivity() : AppCompatActivity(),AppContext {
    companion object {
        var hashMap: HashMap<String, Country> = HashMap()
    }
    val context:AppContext
    init {
        context = this
    }
    override fun loadFragment(layoutId: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(layoutId, fragment)
        transaction.commit()
    }
    override fun showToast(text: String,duration: Int) {
        Toast.makeText(this,text, duration).show()
    }
    override fun getResponseFromNetwork(url: URL):String {
        val httpURLConnection = url.openConnection() as HttpURLConnection
        var inputStream = httpURLConnection.inputStream as InputStreamReader
        val scanner = Scanner(inputStream)
        scanner.useDelimiter("\\A")
        while(scanner.hasNext()){
            return scanner.next()
        }
        return ""
    }
    override fun getContext(): Context {
        return applicationContext
    }
    override fun getCountriesHashMap(): HashMap<String, Country> {
        if(hashMap.size<=0) {
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
                    hashMap.put(arrName,country)
                }
            }
            typedArray.recycle()
        }
        return hashMap
    }
}